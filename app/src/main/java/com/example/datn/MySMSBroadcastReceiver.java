package com.example.datn;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.widget.EditText;

public class MySMSBroadcastReceiver extends BroadcastReceiver {
    private static EditText input_number;

    @Override
    public void onReceive(Context context, Intent intent) {
        SmsMessage[] smsMessages = Telephony.Sms.Intents.getMessagesFromIntent(intent);
        for (SmsMessage smsRetriever : smsMessages) {
            String message_body = smsRetriever.getMessageBody();
            String getOTP = message_body.split(":")[1];
            input_number.setText(getOTP);
        }
    }

    public void setInput_number(EditText editText) {
        MySMSBroadcastReceiver.input_number = editText;
    }
}
