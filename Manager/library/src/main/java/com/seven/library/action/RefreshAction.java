package com.seven.library.action;

import com.frankchen.mvc.aidl.Task;
import com.frankchen.mvc.model.clientaction.common.TCAction;
import com.seven.library.config.RunTimeConfig;

/**
 * 刷新
 *
 * @author seven
 */
public class RefreshAction extends TCAction {
    @Override
    public boolean doTaskAtLocal(Task task) {

        notificateToUI(task);

        return false;
    }

    @Override
    public Task getTask(int what) {
        Task task = Task.obtain(false);
        task.setWhat(what);
        return task;
    }

    @Override
    public String getActionName() {
        return RunTimeConfig.ActionConfig.REFRESH;
    }
}
