package com.SleeplessStudios.modernpocket;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class TasksMain extends AppCompatActivity {
    private ImageButton filter;
    private ImageButton sidebar;
    private ImageButton createTask;

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private EditText taskName;
    private EditText taskDueDate;
    private Button create;
    private Button cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks_main);

        filter = (ImageButton) findViewById(R.id.filter_tasks_btn);
        sidebar = (ImageButton) findViewById(R.id.burgerbar_tasks_btn);
        createTask = (ImageButton) findViewById(R.id.create_task_btn);

        createTask.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                OpenTaskDialog();
            }
        });
    }

    public void OpenTaskDialog()
    {
        dialogBuilder = new AlertDialog.Builder(this);
        final View taskDialogView = getLayoutInflater().inflate(R.layout.dialog_task_create, null);

        dialogBuilder.setView(taskDialogView);
        taskName = (EditText) taskDialogView.findViewById(R.id.editable_task_name_txt);
        taskDueDate = (EditText) taskDialogView.findViewById(R.id.editable_due_date_txt);
        create = (Button) taskDialogView.findViewById(R.id.confirm_create_task_btn);
        cancel = (Button) taskDialogView.findViewById(R.id.cancel_task_btn);

        dialogBuilder.setView(taskDialogView);

        dialog = dialogBuilder.create();
        dialog.show();

        create.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                CreateTask();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                dialog.dismiss();
            }
        });
    }

    public void CreateTask()
    {

    }
}