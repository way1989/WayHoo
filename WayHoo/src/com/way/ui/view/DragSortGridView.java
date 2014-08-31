package com.way.ui.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.DragEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.GridView;

import com.way.common.util.L;
import com.way.yahoo.R;

@SuppressLint("NewApi")
public class DragSortGridView extends GridView {

	private static final String TAG = "drag-sort-gridview";
	private static final boolean DEBUG_LOG = false;

	public DragSortGridView(Context context) {
		this(context, null);
	}

	public DragSortGridView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public DragSortGridView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		setOnItemLongClickListener(itemLongClickListener);
		setOnDragListener(dragListener);
	}

	/**
	 * Only user of this view know how to update content data to change their
	 * order. So they need this callback.
	 */
	public interface OnReorderingListener {
		public void beginRecordering(AdapterView<?> parent, View view,
				int position, long id);

		public void onReordering(int fromPosition, int toPosition);
	}

	public void setOnReorderingListener(OnReorderingListener listener) {
		this.onReorderingListener = listener;
	}

	protected OnReorderingListener onReorderingListener;

	protected OnItemLongClickListener itemLongClickListener = new OnItemLongClickListener() {

		@Override
		public boolean onItemLongClick(AdapterView<?> parent, View view,
				int position, long id) {
			view.startDrag(null, new View.DragShadowBuilder(view), position, 0);
			if (onReorderingListener != null)
				onReorderingListener.beginRecordering(parent, view, position,
						id);
			return true;
		}

	};

	/**
	 * Between {@link DragEvent.ACTION_DRAG_STARTED} and
	 * {@link DragEvent.ACTION_DRAG_ENDED}, grid view children are moving around
	 * and lost their original positions. So we need a method to keep track of
	 * the changing positions.
	 */
	private class IntegerArray {
		private int[] data;

		public IntegerArray(int size) {
			int[] _data = new int[size];
			for (int i = 0; i < size; ++i) {
				_data[i] = i;
			}
			this.data = _data;
		}

		public int get(int position) {
			return data[position];
		}

		/**
		 * Move array value from one position to another.
		 * 
		 * Example: [0, 1, 2, 3, 4], move from 1 to 3 results: [0, 2, 3, 1, 4].
		 * 
		 * Another: [0, 1, 2, 3, 4], move from 3 to 1 results: [0, 3, 1, 2, 4].
		 */
		public void reorder(int from, int to) {
			if (from == to) {
				return;
			}

			final int[] array = data;

			if (from < to) {
				int fromValue = array[from];
				for (int i = from; i < to; ++i) {
					array[i] = array[i + 1];
				}
				array[to] = fromValue;
			} else {
				int fromValue = array[from];
				for (int i = from; i > to; --i) {
					array[i] = array[i - 1];
				}
				array[to] = fromValue;
			}
		}

		public int getValueIndex(int value) {
			final int[] array = data;
			final int size = array.length;

			for (int i = 0; i < size; ++i) {
				if (value == array[i]) {
					return i;
				}
			}
			return -1;
		}

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("[");

			final int[] array = data;
			final int size = array.length;
			for (int i = 0; i < size; i++) {
				builder.append(Integer.toString(array[i])).append(",");
			}
			builder.append("]");
			return builder.toString();
		}
	}

	protected IntegerArray reorderingPositions;

	protected int lastDraggingPosition;
	protected boolean movingChildViews = false;
	private Animation fadeOutAnimation;

	private View.OnDragListener dragListener = new View.OnDragListener() {

		@Override
		public boolean onDrag(View v, DragEvent event) {
			final int action = event.getAction();
			final int x = Math.round(event.getX());
			final int y = Math.round(event.getY());
			if (DEBUG_LOG) {
				L.d(TAG,
						"onDrag event action:" + DRAG_EVENT_ACTION.get(action)
								+ ",x:" + x + ",y:" + y);
			}

			switch (action) {
			case DragEvent.ACTION_DRAG_STARTED:
				// notice: init array with adapter item count, NOT child view
				// count.
				reorderingPositions = new IntegerArray(getAdapter().getCount());

				lastDraggingPosition = (Integer) event.getLocalState();

				if (fadeOutAnimation == null) {
					fadeOutAnimation = AnimationUtils.loadAnimation(
							getContext(), R.anim.fade_out);
				}
				getView(lastDraggingPosition).startAnimation(fadeOutAnimation);
				break;
			case DragEvent.ACTION_DRAG_ENTERED:
				break;
			case DragEvent.ACTION_DRAG_LOCATION: {
				if (movingChildViews)
					break;

				// Watch out: pointToPosition ignores invisible views.
				final int pos = pointToPosition(x, y);
				final int newPosition = pos == -1 ? -1 : reorderingPositions
						.getValueIndex(pos);

				if (-1 != newPosition && lastDraggingPosition != newPosition) {
					if (DEBUG_LOG) {
						L.d(TAG, "ACTION_DRAG_LOCATION lastPosition:"
								+ lastDraggingPosition + ",newPosition:"
								+ newPosition);
					}

					reorderViews(lastDraggingPosition, newPosition);
					reorderingPositions.reorder(lastDraggingPosition,
							newPosition);
					lastDraggingPosition = newPosition;

					if (DEBUG_LOG) {
						L.d(TAG, "reordering positions:"
								+ reorderingPositions);
					}

					movingChildViews = true;
				}
				break;
			}
			case DragEvent.ACTION_DRAG_EXITED:
				break;
			case DragEvent.ACTION_DROP: {
				final int pos = pointToPosition(x, y);
				final int newPosition = pos == -1 ? -1 : reorderingPositions
						.getValueIndex(pos);

				if (DEBUG_LOG) {
					L.d(TAG, "ACTION_DROP pos:" + pos + ",newPosition:"
							+ newPosition);
				}

				getView(lastDraggingPosition).clearAnimation();

				if (onReorderingListener != null) {
					final int oldPosition = (Integer) event.getLocalState();
					if (newPosition != -1 && newPosition != oldPosition) {
						onReorderingListener.onReordering(oldPosition,
								newPosition);
					}
				}
				break;
			}
			case DragEvent.ACTION_DRAG_ENDED:
				break;
			}
			return true;
		}
	};

	/**
	 * Move child view from one position to another.
	 * 
	 * Example: [0, 1, 2, 3, 4], move from 1 to 3 results: [0, 2, 3, 1, 4].
	 * 
	 * Another: [0, 1, 2, 3, 4], move from 3 to 1 results: [0, 3, 1, 2, 4].
	 */
	protected void reorderViews(int fromPosition, int toPosition) {
		if (DEBUG_LOG) {
			L.d(TAG, "reorderViews old:" + fromPosition + ",new:"
					+ toPosition);
		}

		if (fromPosition == toPosition) {
			return;
		}

		final View fromChild = getView(fromPosition);
		final View toChild = getView(toPosition);

		Rect toLayout = new Rect();

		if (toPosition < fromPosition) {
			// preserve view layout on new position, for it will be changed on
			// next step.
			getLayout(toChild, toLayout);

			for (int i = toPosition; i < fromPosition; ++i) {
				moveView(i, i + 1);
			}

			fromChild.layout(toLayout.left, toLayout.top, toLayout.right,
					toLayout.bottom);
		} else {
			// preserve view layout on new position, for it will be changed on
			// next step.
			getLayout(toChild, toLayout);

			for (int i = toPosition; i > fromPosition; --i) {
				moveView(i, i - 1);
			}

			fromChild.layout(toLayout.left, toLayout.top, toLayout.right,
					toLayout.bottom);
		}
	}

	private void moveView(int fromPosition, int toPosition) {
		if (DEBUG_LOG) {
			L.d(TAG, "moveView from:" + fromPosition + ",to:" + toPosition);
		}

		final View from = getView(fromPosition);
		final View to = getView(toPosition);

		final Rect fromRect = new Rect();
		getLayout(from, fromRect);
		final Rect toRect = new Rect();
		getLayout(to, toRect);

		Animation translate = new TranslateAnimation(0, toRect.left
				- fromRect.left, 0, toRect.top - fromRect.top);
		translate.setDuration(150);
		translate.setFillEnabled(true);
		translate.setFillBefore(true);
		translate.setFillAfter(true);
		translate.setAnimationListener(new MoveViewAnimationListener(from, to
				.getLeft(), to.getTop()));

		from.startAnimation(translate);
	}

	private class MoveViewAnimationListener implements
			Animation.AnimationListener {

		private View target;
		private int newX, newY;

		public MoveViewAnimationListener(View target, int newX, int newY) {
			this.target = target;
			this.newX = newX;
			this.newY = newY;
		}

		@Override
		public void onAnimationEnd(Animation animation) {
			target.layout(newX, newY, newX + target.getWidth(),
					newY + target.getHeight());
			target.clearAnimation();
			movingChildViews = false;
		}

		@Override
		public void onAnimationRepeat(Animation animation) {
		}

		@Override
		public void onAnimationStart(Animation animation) {
		}

	}

	private void getLayout(View view, Rect rect) {
		rect.set(view.getLeft(), view.getTop(), view.getRight(),
				view.getBottom());
	}

	private View getView(int reorderingPosition) {
		int orgPosition = reorderingPositions.get(reorderingPosition);
		return getChildAt(orgPosition - getFirstVisiblePosition());
	}

	private static final SparseArray<String> DRAG_EVENT_ACTION = DEBUG_LOG ? new SparseArray<String>()
			: null;
	static {
		if (DEBUG_LOG) {
			DRAG_EVENT_ACTION.put(DragEvent.ACTION_DRAG_STARTED,
					"ACTION_DRAG_STARTED");
			DRAG_EVENT_ACTION.put(DragEvent.ACTION_DRAG_ENTERED,
					"ACTION_DRAG_ENTERED");
			DRAG_EVENT_ACTION.put(DragEvent.ACTION_DRAG_LOCATION,
					"ACTION_DRAG_LOCATION");
			DRAG_EVENT_ACTION.put(DragEvent.ACTION_DRAG_EXITED,
					"ACTION_DRAG_EXITED");
			DRAG_EVENT_ACTION.put(DragEvent.ACTION_DROP, "ACTION_DROP");
			DRAG_EVENT_ACTION.put(DragEvent.ACTION_DRAG_ENDED,
					"ACTION_DRAG_ENDED");
		}
	}
}
