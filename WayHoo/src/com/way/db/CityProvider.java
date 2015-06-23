package com.way.db;

import java.util.ArrayList;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.provider.BaseColumns;
import android.text.TextUtils;

import com.way.common.util.L;
import com.way.common.util.SystemUtils;

public class CityProvider extends ContentProvider {
	private static final String TAG = "CityProvider";

	public static final String CITY_DB_NAME = "city.db";
	public static final String WRITE_TMPCITY = "write_tmpcity";
	public static final String AUTHORITY = "com.way.yahoo.provider.Citys";// 授权
	public static final String CITY_TABLE_NAME = "city";// 城市表名
	public static final String HOTCITY_TABLE_NAME = "hotcity";// 热门城市表名
	public static final String TMPCITY_TABLE_NAME = "tmpcity";// 临时城市表名

	public static final Uri CITY_CONTENT_URI = Uri.parse("content://"
			+ AUTHORITY + "/" + CITY_TABLE_NAME);// 城市uri
	public static final Uri HOTCITY_CONTENT_URI = Uri.parse("content://"
			+ AUTHORITY + "/" + HOTCITY_TABLE_NAME);// 热门城市 uri
	public static final Uri TMPCITY_CONTENT_URI = Uri.parse("content://"
			+ AUTHORITY + "/" + TMPCITY_TABLE_NAME);// 临时城市 uri

	private static final UriMatcher URI_MATCHER = new UriMatcher(
			UriMatcher.NO_MATCH);// 匹配

	private static final int CITYS = 1;// 多个城市查询
	private static final int CITY_ID = 2;// 单个城市查询
	private static final int HOTCITYS = 3;// 多个热门城市查询
	private static final int HOTCITY_ID = 4;// 单个热门城市查询
	private static final int TMPCITYS = 5;// 多个临时查询
	private static final int TMPCITY_ID = 6;// 单个临时查询
	static {
		URI_MATCHER.addURI(AUTHORITY, "city", CITYS);
		URI_MATCHER.addURI(AUTHORITY, "city/#", CITY_ID);

		URI_MATCHER.addURI(AUTHORITY, "hotcity", HOTCITYS);
		URI_MATCHER.addURI(AUTHORITY, "hotcity/#", HOTCITY_ID);

		URI_MATCHER.addURI(AUTHORITY, "tmpcity", TMPCITYS);
		URI_MATCHER.addURI(AUTHORITY, "tmpcity/#", TMPCITY_ID);
	}

	private SQLiteDatabase mSqLiteDatabase;

	// private CityProvider() {
	// }
	public static void createTmpCityTable(Context context) {
		SQLiteDatabase db = context.openOrCreateDatabase(
				SystemUtils.getDBFilePath(context), Context.MODE_PRIVATE, null);
		L.i("liweiping", "create table tmpcity ....");
		db.execSQL("CREATE table IF NOT EXISTS "
				+ TMPCITY_TABLE_NAME
				+ " (_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, postID TEXT,"
				+ " refreshTime TEXT, isLocation TEXT, pubTime TEXT, weatherInfo TEXT, orderIndex INTEGER)");
	}

	@Override
	public boolean onCreate() {
		mSqLiteDatabase = getContext().openOrCreateDatabase(
				SystemUtils.getDBFilePath(getContext()), Context.MODE_PRIVATE,
				null);
		L.i("liweiping", "create db....");
		return true;
	}

	private static void infoLog(String data) {
		L.i(TAG, data);
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		SQLiteQueryBuilder qBuilder = new SQLiteQueryBuilder();
		int match = URI_MATCHER.match(uri);
		// 允许对三个表进行查询
		switch (match) {
		case CITYS:
			qBuilder.setTables(CITY_TABLE_NAME);
			break;
		case CITY_ID:
			qBuilder.setTables(CITY_TABLE_NAME);
			qBuilder.appendWhere("_id=");
			qBuilder.appendWhere(uri.getPathSegments().get(1));
			break;
		case HOTCITYS:
			qBuilder.setTables(HOTCITY_TABLE_NAME);
			break;
		case HOTCITY_ID:
			qBuilder.setTables(HOTCITY_TABLE_NAME);
			qBuilder.appendWhere("_id=");
			qBuilder.appendWhere(uri.getPathSegments().get(1));
			break;
		case TMPCITYS:
			qBuilder.setTables(TMPCITY_TABLE_NAME);
			break;
		case TMPCITY_ID:
			qBuilder.setTables(TMPCITY_TABLE_NAME);
			qBuilder.appendWhere("_id=");
			qBuilder.appendWhere(uri.getPathSegments().get(1));
			break;
		default:
			throw new IllegalArgumentException("Unknown URL " + uri);
		}

		String orderBy;
		if (TextUtils.isEmpty(sortOrder)) {
			orderBy = CityConstants.DEFAULT_SORT_ORDER;
		} else {
			orderBy = sortOrder;
		}

		Cursor ret = qBuilder.query(mSqLiteDatabase, projection, selection,
				selectionArgs, null, null, orderBy);

		if (ret == null) {
			infoLog("CityProvider.query: failed");
		} else {
			ret.setNotificationUri(getContext().getContentResolver(), uri);
		}
		return ret;
	}

	@Override
	public String getType(Uri uri) {
		int match = URI_MATCHER.match(uri);
		switch (match) {
		case CITYS:
			return CityConstants.CONTENT_TYPE;
		case CITY_ID:
			return CityConstants.CONTENT_ITEM_TYPE;
		default:
			throw new IllegalArgumentException("Unknown URL");
		}
	}

	@Override
	public Uri insert(Uri uri, ContentValues initialValues) {
		// 只允许存储到临时城市表
		if (URI_MATCHER.match(uri) != TMPCITYS) {
			throw new IllegalArgumentException("Cannot insert into URL: " + uri);
		}

		ContentValues values = (initialValues != null) ? new ContentValues(
				initialValues) : new ContentValues();

		// for (String colName : CityConstants.getRequiredColumns()) {
		// if (values.containsKey(colName) == false) {
		// throw new IllegalArgumentException("Missing column: " + colName);
		// }
		// }

		long rowId = mSqLiteDatabase.insert(TMPCITY_TABLE_NAME,
				CityConstants.REFRESH_TIME, values);

		if (rowId < 0) {
			throw new SQLException("Failed to insert row into " + uri);
		}

		Uri noteUri = ContentUris.withAppendedId(TMPCITY_CONTENT_URI, rowId);
		getContext().getContentResolver().notifyChange(noteUri, null);// 发出通知
		return noteUri;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		SQLiteDatabase db = mSqLiteDatabase;
		int count;
		// 只允许删除临时表中的数据
		switch (URI_MATCHER.match(uri)) {
		case TMPCITYS:
			count = db.delete(TMPCITY_TABLE_NAME, selection, selectionArgs);
			break;
		case TMPCITY_ID:
			String segment = uri.getPathSegments().get(1);

			if (TextUtils.isEmpty(selection)) {
				selection = "_id=" + segment;
			} else {
				selection = "_id=" + segment + " AND (" + selection + ")";
			}
			count = db.delete(TMPCITY_TABLE_NAME, selection, selectionArgs);
			break;
		default:
			throw new IllegalArgumentException("Cannot delete from URL: " + uri);
		}
		getContext().getContentResolver().notifyChange(uri, null);
		return count;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		int count;
		long rowId = 0;
		int match = URI_MATCHER.match(uri);
		SQLiteDatabase db = mSqLiteDatabase;
		// 可以更新热门城市表和临时城市表
		switch (match) {
//		case HOTCITYS:
//			count = db.update(HOTCITY_TABLE_NAME, values, selection,
//					selectionArgs);
//			break;
//		case HOTCITY_ID:
//			String hotSegment = uri.getPathSegments().get(1);
//			rowId = Long.parseLong(hotSegment);
//			count = db.update(HOTCITY_TABLE_NAME, values, "_id=" + rowId, null);
//			break;
		case TMPCITYS:
			count = db.update(TMPCITY_TABLE_NAME, values, selection,
					selectionArgs);
			break;
		case TMPCITY_ID:
			String tmpSegment = uri.getPathSegments().get(1);
			rowId = Long.parseLong(tmpSegment);
			count = db.update(TMPCITY_TABLE_NAME, values, "_id=" + rowId, null);
			break;
		default:
			throw new UnsupportedOperationException("Cannot update URL: " + uri);
		}
		infoLog("*** notifyChange() rowId: " + rowId + " url " + uri);
		getContext().getContentResolver().notifyChange(uri, null);
		return count;
	}

	public static final class CityConstants implements BaseColumns {
		public static final String CONTENT_TYPE = "vnd.android.cursor.dir/city";
		public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/city";

		public static final String ID = "_id";
		public static final String PROVINCE = "province";
		public static final String CITY = "city";
		public static final String NAME = "name";
		public static final String PINYIN = "pinyin";
		public static final String PY = "py";
		public static final String PHONE_CODE = "phoneCode";
		public static final String AREA_CODE = "areaCode";
		public static final String LONGITUDE = "longitude";
		public static final String LATITUDE = "latitude";
		public static final String POST_ID = "postID";

		public static final String REFRESH_TIME = "refreshTime";// 临时城市列表刷新时间
		public static final String PUB_TIME = "pubTime";// 临时城市列表刷新时间
		public static final String WEATHER_INFO = "weatherInfo";// 临时城市列表刷新时间
		public static final String ISLOCATION = "isLocation";
		public static final String ORDER_INDEX = "orderIndex";

		public static final String DEFAULT_SORT_ORDER = "_id ASC"; // 默认按照_id排序

		private CityConstants() {
		}

		public static ArrayList<String> getRequiredColumns() {
			ArrayList<String> tmpList = new ArrayList<String>();
			// tmpList.add(PROVINCE);
			// tmpList.add(CITY);
			tmpList.add(NAME);
			// tmpList.add(PINYIN);

			// tmpList.add(PY);
			// tmpList.add(PHONE_CODE);
			// tmpList.add(AREA_CODE);
			// tmpList.add(LONGITUDE);
			// tmpList.add(LATITUDE);
			tmpList.add(POST_ID);
			tmpList.add(REFRESH_TIME);
			return tmpList;
		}

		public static String[] getCityDefaultProjection() {
			return new String[] { PROVINCE, CITY, NAME, PINYIN, PY, PHONE_CODE,
					AREA_CODE, POST_ID };
		}
	}
}
