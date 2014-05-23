package com.example.demo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.tencent.mm.sdk.openapi.*;

public class MyActivity extends Activity {
    private EditText text;
    private Button btn;
    private IWXAPI api;
    private static final String APP_ID = "wx7c10904d75e9e1b6";

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        text = (EditText)this.findViewById(R.id.et_content);
        btn = (Button)this.findViewById(R.id.btn_share);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendWeiXinText(text.getText().toString());
            }
        });
    }

    private void sendWeiXinText(String content){
        api = WXAPIFactory.createWXAPI(this, APP_ID, false);
        api.registerApp(APP_ID);

        WXTextObject textObj = new WXTextObject();
        textObj.text = content;

        // ��WXTextObject�����ʼ��һ��WXMediaMessage����
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = textObj;
        // �����ı����͵���Ϣʱ��title�ֶβ�������
        // msg.title = "Will be ignored";
        msg.description = content;

        // ����һ��Req
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("text"); // transaction�ֶ�����Ψһ��ʶһ������
        req.message = msg;
//        req.scene = isTimelineCb.isChecked() ? SendMessageToWX.Req.WXSceneTimeline : SendMessageToWX.Req.WXSceneSession;
        req.scene = SendMessageToWX.Req.WXSceneSession;

        // ����api�ӿڷ�����ݵ�΢��
        api.sendReq(req);
    }
    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }
}
