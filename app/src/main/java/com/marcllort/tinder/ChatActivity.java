package com.marcllort.tinder;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.marcllort.tinder.Model.Message;
import com.marcllort.tinder.Model.User;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {
    private RecyclerView mMessageRecycler;
    private MessageListAdapter mMessageAdapter;
    private EditText mChatBox;
    private Button mSendButton;
    private Toolbar mToolbar;
    private String user;
    private List<Message> messageList;
    private NestedScrollView nestedScroll;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Bundle extras = getIntent().getExtras();
        if(extras !=null) {
            user = extras.getString("login");
        }


        messageList = new ArrayList<>();
        for (int i = 0; i < 27; i++) {
            messageList.add(new Message("Rebo" + i, new User(user, "sd", "ES")));
            messageList.add(new Message("Envio" + i, null));
        }

        mMessageRecycler = (RecyclerView) findViewById(R.id.reyclerview_message_list);
        mMessageRecycler.setNestedScrollingEnabled(false);
        mMessageRecycler.setHasFixedSize(false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ChatActivity.this);

        //linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);

        mMessageRecycler.setLayoutManager(linearLayoutManager);



        mMessageAdapter = new MessageListAdapter(this, getChatData());
        mMessageRecycler.setAdapter(mMessageAdapter);

        mChatBox = (EditText) findViewById(R.id.edittext_chatbox);
        mSendButton = (Button) findViewById(R.id.button_chatbox_send);

        topBarSetup();
        mMessageRecycler.smoothScrollToPosition(messageList.size() - 1);
        /*nestedScroll = findViewById(R.id.nested);

        nestedScroll.post(new Runnable() {
            @Override
            public void run() {
                nestedScroll.fullScroll(View.FOCUS_DOWN);
            }
        });*/

        mChatBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                if (charSequence.toString().trim().length() > 0) {
                    mSendButton.setEnabled(true);
                } else {
                    mSendButton.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        // Send text button
        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });


    }



    private void topBarSetup(){
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        TextView title= findViewById(R.id.text_toolbar_title);
        title.setText(user);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    private void getChatMessages() {

        Message message = new Message("provaaa", new User(user, "sd", "ES"));
        if (message != null && message.getSender() != null) {
            Boolean isCurrentUser = message.getSender().equals(0);

            mMessageAdapter.notifyDataSetChanged();
        }
    }


    private void sendMessage() {

        String messageText = mChatBox.getText().toString();

        if (!messageText.isEmpty()) {
            messageList.add(new Message(messageText, null));
        }
        getChatMessages();

        // clear text after hitting send;
        mChatBox.setText(null);

        mMessageRecycler.smoothScrollToPosition(messageList.size() - 1);

        /*nestedScroll.post(new Runnable() {
            @Override
            public void run() {
                nestedScroll.fullScroll(View.FOCUS_DOWN);
            }
        });*/
    }

    private List<Message> getChatData() {
        return messageList;
    }


}