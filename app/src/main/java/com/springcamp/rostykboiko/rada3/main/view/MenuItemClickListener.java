package com.springcamp.rostykboiko.rada3.main.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;

import com.springcamp.rostykboiko.rada3.R;

class MenuItemClickListener implements PopupMenu.OnMenuItemClickListener {
    private CardViewHolder.QuestionCardCallback callback;

    private int position;

    MenuItemClickListener(int position, @NonNull final CardViewHolder.QuestionCardCallback callback) {
        this.position = position;
        this.callback = callback;
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.action_edit:
                callback.onEditClick(position);
                return true;
            case R.id.action_delete:
                callback.onDeleteCard(position);
                return true;
            default:
                break;
        }
        return false;
    }
}