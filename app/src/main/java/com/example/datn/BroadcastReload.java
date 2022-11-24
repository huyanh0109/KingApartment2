package com.example.datn;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

public class BroadcastReload extends NetworkBroadcast {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (!isNetworkConnected(context)) {
            final Dialog dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialog_checkconnect);
            Button btn_tryagain = dialog.findViewById(R.id.btn_tryagain);
            btn_tryagain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (isNetworkConnected(context)) {
                        dialog.dismiss();
                        restart(context);
                    }
                }
            });
            dialog.show();
            dialog.setCancelable(false);
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().getAttributes().windowAnimations = R.style.dialogAnimation;
            dialog.getWindow().setGravity(Gravity.BOTTOM);
        }
    }
}
