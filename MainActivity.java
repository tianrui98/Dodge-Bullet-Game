package io.github.bbodin.yncgamelab;

import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.appcompat.app.AppCompatActivity;

import io.github.bbodin.yncgamelab.io.InputHandler;
import io.github.bbodin.yncgamelab.io.InputListener;
import io.github.bbodin.yncgamelab.models.CellStatus;
import io.github.bbodin.yncgamelab.models.gems.ChangeAction;
import io.github.bbodin.yncgamelab.models.gems.GemGrid;
import io.github.bbodin.yncgamelab.models.gems.GemSolver;
import io.github.bbodin.yncgamelab.models.gems.MoveAction;
import io.github.bbodin.yncgamelab.rendering.GridRenderer;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private  final static String TAG = "MainActivity";
    GemGrid     _grid         = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        CheckBox    _editCheckBox = null;
        Button      _solveButton  = null;
        Button      _resetButton  = null;
        SurfaceView _sv           = null;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "onCreate");


        _grid = GemGrid.generateDemo();

        _editCheckBox =  (CheckBox) findViewById(R.id.EditCheckBox);
        _editCheckBox.setChecked(_grid.isEditable());
        _editCheckBox.setOnClickListener(this);

        _resetButton = (Button) findViewById(R.id.resetButton);
        _resetButton.setOnClickListener(this);

        _solveButton = (Button) findViewById(R.id.solveButton);
        _solveButton.setOnClickListener(this);

        GridRenderer gridRenderer = new GridRenderer(_grid);
        _grid.addCallBack(gridRenderer);

        _sv =  (SurfaceView)findViewById(R.id.surfaceView);
        _sv.setWillNotDraw(false);
        _sv.getHolder().addCallback(gridRenderer);

        InputListener inputListener = new InputListener();
        _sv.setOnTouchListener(inputListener);

        InputHandler inputHandler = new InputHandler();
        inputHandler.setOnClickAction(new ChangeAction(_grid, CellStatus.BLUE));
        inputHandler.setOnLeftSwipeAction(new MoveAction(_grid, _grid.left));
        inputHandler.setOnRightSwipeAction(new MoveAction(_grid, _grid.right));
        inputListener.setCallback(inputHandler);


        Log.d(TAG, "onCreate Finished");

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.resetButton) {
            this._grid.Reset();
        } else if (view.getId() == R.id.solveButton) {
            GemSolver.solve(_grid);
        } else if (view.getId() == R.id.EditCheckBox) {
            CheckBox cb = (CheckBox) view;
            _grid.setEditable(cb.isChecked());
        }
    }
}
