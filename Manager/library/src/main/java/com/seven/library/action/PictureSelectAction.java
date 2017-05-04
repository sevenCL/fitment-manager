package com.seven.library.action;

import com.frankchen.mvc.aidl.Task;
import com.frankchen.mvc.model.clientaction.common.TCAction;
import com.seven.library.config.RunTimeConfig;

/**
 * 选择图片
 *
 * @author seven
 */
public class PictureSelectAction extends TCAction {
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
        return RunTimeConfig.ActionConfig.PICTURE_SELECT;
    }
}
