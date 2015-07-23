package com.way.fragment;


import java.io.Serializable;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.way.yahoo.App;
import com.way.yahoo.R;

/**
 * Created by wangdan on 15-1-16.
 */
public abstract class BaseFragment extends Fragment implements ITaskManager {
    static final String TAG = BaseFragment.class.getSimpleName();

    protected enum ABaseTaskState {
        none, prepare, falid, success, finished, canceled
    }

    private ViewGroup rootView;// 根视图
    private TaskManager taskManager;// 管理线程

    View loadingLayout;// 加载中视图
    View loadFailureLayout;// 加载失败视图
    View contentLayout;// 内容视图
    View emptyLayout;// 空视图

    // 标志是否ContentView是否为空
    private boolean contentEmpty = true;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        taskManager = new TaskManager();
        if (savedInstanceState != null)
            taskManager.restore(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (inflateContentView() > 0) {
            rootView = (ViewGroup) inflater.inflate(inflateContentView(), null);
            rootView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

            _layoutInit(inflater, savedInstanceState);

            layoutInit(inflater, savedInstanceState);

            return rootView;
        }

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    /**
     * 根视图
     *
     * @return
     */
    ViewGroup getRootView() {
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState == null)
            requestData();
    }

    /**
     * Action的home被点击了
     *
     * @return
     */
    public boolean onHomeClick() {
        return onBackClick();
    }

    /**
     * 返回按键被点击了
     *
     * @return
     */
    public boolean onBackClick() {
        return false;
    }

    /**
     * 初次创建时默认会调用一次
     */
    public void requestData() {

    }

    /**
     * A*Fragment重写这个方法
     *
     * @param inflater
     * @param savedInstanceSate
     */
    void _layoutInit(LayoutInflater inflater, Bundle savedInstanceSate) {
        loadingLayout = rootView.findViewById(R.id.layoutLoading);
        loadFailureLayout = rootView.findViewById(R.id.layoutLoadFailed);
        contentLayout = rootView.findViewById(R.id.layoutContent);
        emptyLayout = rootView.findViewById(R.id.layoutEmpty);
        if (emptyLayout != null) {
            View reloadView = emptyLayout.findViewById(R.id.layoutReload);
            if (reloadView != null)
                setViewOnClick(reloadView);
        }

        if (loadFailureLayout != null) {
            View reloadView = loadFailureLayout.findViewById(R.id.layoutReload);
            if (reloadView != null)
                setViewOnClick(reloadView);
        }

        setViewVisiable(loadingLayout, View.GONE);
        setViewVisiable(loadFailureLayout, View.GONE);
        setViewVisiable(emptyLayout, View.GONE);
        if (isContentEmpty()) {
//            if (savedInstanceSate != null) {
//                requestData();
//            }
//            else {
                setViewVisiable(emptyLayout, View.VISIBLE);
                setViewVisiable(contentLayout, View.GONE);
//            }
        }
        else {
            setViewVisiable(contentLayout, View.VISIBLE);
        }
    }

    /**
     * 子类重写这个方法，初始化视图
     *
     * @param inflater
     * @param savedInstanceSate
     */
    protected void layoutInit(LayoutInflater inflater, Bundle savedInstanceSate) {

    }

    protected View findViewById(int viewId) {
        if (rootView == null)
            return null;

        return rootView.findViewById(viewId);
    }

    abstract protected int inflateContentView();

    public void setContentEmpty(boolean empty) {
        this.contentEmpty = empty;
    }

    public boolean isContentEmpty() {
        return contentEmpty;
    }

    /**
     * 视图点击回调，子类重写
     *
     * @param view
     */
    public void onViewClicked(View view) {
        if (view.getId() == R.id.layoutReload)
            requestData();
//        else if (view.getId() == R.id.layoutRefresh)
//            requestData();
    }

    void setViewVisiable(View v, int visibility) {
        if (v != null)
            v.setVisibility(visibility);
    }

    /**
     * 根据{@link ABaseTask} 的加载状态，刷新视图
     *
     * @param state
     *
     * @param tag
     */
    protected void taskStateChanged(ABaseTaskState state, Serializable tag) {
        // 开始Task
        if (state == ABaseTaskState.prepare) {
            if (isContentEmpty()) {
                setViewVisiable(loadingLayout, View.VISIBLE);

                setViewVisiable(contentLayout, View.GONE);
            }
            else {
                setViewVisiable(loadingLayout, View.GONE);

                setViewVisiable(contentLayout, View.VISIBLE);
            }

            setViewVisiable(emptyLayout, View.GONE);
            setViewVisiable(loadFailureLayout, View.GONE);
        }
        // Task成功
        else if (state == ABaseTaskState.success) {
            setViewVisiable(loadingLayout, View.GONE);

            if (isContentEmpty()) {
                setViewVisiable(emptyLayout, View.VISIBLE);
            }
            else {
                setViewVisiable(contentLayout, View.VISIBLE);
            }
        }
        // 取消Task
        else if (state == ABaseTaskState.canceled) {
            if (isContentEmpty()) {
                setViewVisiable(loadingLayout, View.GONE);
                setViewVisiable(emptyLayout, View.VISIBLE);
            }
        }
        // Task失败
        else if (state == ABaseTaskState.falid) {
            if (isContentEmpty()) {
                setViewVisiable(emptyLayout, View.GONE);
                setViewVisiable(loadingLayout, View.GONE);
                setViewVisiable(loadFailureLayout, View.VISIBLE);
                if (tag != null && loadFailureLayout != null)
                    setTextViewValue(loadFailureLayout, R.id.txtLoadFailed, tag.toString());
            }
        }
        // Task结束
        else if (state == ABaseTaskState.finished) {

        }
    }
	public void setTextViewValue(View container, int txtId, String content) {
		((TextView) container.findViewById(txtId)).setText(content);
	}
    /**
     * 以Toast形式显示一个消息
     *
     * @param msg
     */
    protected void showMessage(CharSequence msg) {
        if (!TextUtils.isEmpty(msg))
        	Toast.makeText(App.getApplication(), msg.toString(), Toast.LENGTH_SHORT).show();
    }

    /**
     * 参照{@linkplain #showMessage(String)}
     *
     * @param msgId
     */
    protected void showMessage(int msgId) {
        if (getActivity() != null)
            showMessage(getString(msgId));
    }

    /**
     * Fragment主要的刷新任务线程，定义任务加载流程，耦合Fragment各个状态下的视图刷新方法
     * {@link ABaseFragment#taskStateChanged(ABaseTaskState, Object)}
     *
     * @author wangdan
     *
     * @param <Params>
     * @param <Progress>
     * @param <Result>
     */
    protected abstract class ABaseTask<Params, Progress, Result> extends WorkTask<Params, Progress, Result> {

        public ABaseTask(String taskId) {
            super(taskId, BaseFragment.this);
        }

        @Override
        protected void onPrepare() {
            super.onPrepare();

            taskStateChanged(ABaseTaskState.prepare, null);
        }

        @Override
        protected void onSuccess(Result result) {
            super.onSuccess(result);

            // 默认加载数据成功，且ContentView有数据展示
            BaseFragment.this.setContentEmpty(resultIsEmpty(result));

            taskStateChanged(ABaseTaskState.success, null);
        }

        @Override
        protected void onFailure(TaskException exception) {
            super.onFailure(exception);

            taskStateChanged(ABaseTaskState.falid, exception.getMessage());
        }

        @Override
        protected void onFinished() {
            super.onFinished();

            taskStateChanged(ABaseTaskState.finished, null);
        }

        /**
         * 返回数据是否空
         *
         * @param result
         * @return
         */
        protected boolean resultIsEmpty(Result result) {
            return result == null ? true : false;
        }

    }

    @Override
    public void onDestroy() {
        try {
            // 4.1.1必报错，不知道为什么
            super.onDestroy();
        } catch (Exception e) {
            //Logger.logExc(e);
        }

        removeAllTask(true);
    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    @Override
    final public void addTask(@SuppressWarnings("rawtypes") WorkTask task) {
        taskManager.addTask(task);
    }

    @Override
    final public void removeTask(String taskId, boolean cancelIfRunning) {
        taskManager.removeTask(taskId, cancelIfRunning);
    }

    @Override
    final public void removeAllTask(boolean cancelIfRunning) {
        taskManager.removeAllTask(cancelIfRunning);
    }

    @Override
    final public int getTaskCount(String taskId) {
        return taskManager.getTaskCount(taskId);
    }

    protected void cleatTaskCount(String taskId) {
        taskManager.cleatTaskCount(taskId);
    }

    protected void setViewOnClick(View v) {
        if (v == null)
            return;

        v.setOnClickListener(innerOnClickListener);
    }

    View.OnClickListener innerOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            onViewClicked(v);
        }

    };

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (taskManager != null)
            taskManager.save(outState);
    }

    protected ITaskManager getTaskManager() {
        return taskManager;
    }

}
