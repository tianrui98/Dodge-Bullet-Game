package io.github.bbodin.yncgamelab.models.gems;

import android.util.Log;

import io.github.bbodin.yncgamelab.io.ClickAction;
import io.github.bbodin.yncgamelab.models.CellStatus;
import io.github.bbodin.yncgamelab.utils.Assert;
import io.github.bbodin.yncgamelab.utils.Float2;
import io.github.bbodin.yncgamelab.utils.Int2;

public class ChangeAction implements ClickAction {

    public static String TAG = "ChangeAction";

    private GemGrid gemGrid;
    private CellStatus firstStatus;

    public ChangeAction(GemGrid g, CellStatus s) {
        this.gemGrid = g;
        this.firstStatus = s;
    }


    private Int2 surfaceViewPos2GridPos (Float2 svpos) {

        Assert.isTrue(svpos.getX() <= 1, "Only support position as percent.");
        Assert.isTrue(svpos.getY() <= 1, "Only support position as percent.");
        Assert.isTrue(svpos.getX() >= 0, "Only support position as percent.");
        Assert.isTrue(svpos.getY() >= 0, "Only support position as percent.");

        int vx =  (int) (svpos.getX() * this.gemGrid.getW());
        int vy =  (int) ((1 - svpos.getY()) * this.gemGrid.getH());

        Int2 gridPos = new Int2( vx, vy );

        Log.d(TAG, "surfaceViewPos2GridPos (" + svpos + ") = " + gridPos);

        return gridPos;
    }


    @Override
    public void execute(Float2 pos) {
        Log.d(TAG, "execute (" + pos + ") with default " + firstStatus);
        Int2 gridPos = surfaceViewPos2GridPos (pos);
        this.gemGrid.Change(gridPos);
    }

}
