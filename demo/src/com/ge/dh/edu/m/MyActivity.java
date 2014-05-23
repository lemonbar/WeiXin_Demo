package com.ge.dh.edu.m;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.tencent.mm.sdk.openapi.*;

public class MyActivity extends Activity {
    private EditText text;
    private Button btn_shareToFriend;
    private Button btn_shareToFriendGroup;
    private IWXAPI api;
    private static final String APP_ID = "wx7c10904d75e9e1b6";

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        text = (EditText) this.findViewById(R.id.et_content);
        btn_shareToFriend = (Button) this.findViewById(R.id.btn_share_to_friend);
        btn_shareToFriendGroup = (Button) this.findViewById(R.id.btn_share_to_friend_group);
        btn_shareToFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendWeiXinText(text.getText().toString(), false);
            }
        });
        btn_shareToFriendGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendWeiXinText(text.getText().toString(), true);
            }
        });
    }

    private void sendWeiXinText(String content, Boolean isTimeline) {
        api = WXAPIFactory.createWXAPI(this, APP_ID, true);
        api.registerApp(APP_ID);

        //Initial a WXTestObject instance.
        WXTextObject textObj = new WXTextObject();
        textObj.text = content;

        //Initial a WXMediaMessage by using WXTestObject.
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = textObj;
        // msg.title = "Will be ignored";
        msg.description = content;

        //Construct a Req.
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("text"); // unique a request.
        req.message = msg;
        req.scene = isTimeline ? SendMessageToWX.Req.WXSceneTimeline : SendMessageToWX.Req.WXSceneSession;

        //Invoke api to send data to WeiXin.
        api.sendReq(req);
    }

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }
}
