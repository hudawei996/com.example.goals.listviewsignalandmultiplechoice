package com.example.goals.listviewsignalandmultiplechoice.hotfixTest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.goals.listviewsignalandmultiplechoice.R;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

public class HotFixActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hot_fix);

        runJs();

    }

    private void runJs() {
        Toast.makeText(this,"beidianji",Toast.LENGTH_SHORT).show();
        doit(
                "var widgets = Packages.android.widget;\n" +
                        "var view = new widgets.TextView(TheActivity);\n" +
                        "TheActivity.setContentView(view);\n" +
                        "var text = 'Hello Android!\\nThis is JavaScript in action!';\n" +
                        "view.append(text);"
        );
    }

    void doit(String code) {
        // Create an execution environment.
        Context cx = Context.enter();

        // Turn compilation off.
        cx.setOptimizationLevel(-1);

        try {
            // Initialize a variable scope with bindnings for
            // standard objects (Object, Function, etc.)
            Scriptable scope = cx.initStandardObjects();

            // Set a global variable that holds the activity instance.
            ScriptableObject.putProperty(scope, "TheActivity", Context.javaToJS(this, scope));

            // Evaluate the script.
            cx.evaluateString(scope, code, "doit:", 1, null);
        } finally {
            Context.exit();
        }
    }
}
