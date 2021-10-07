package io.github.bbodin.yncgamelab.models.gems;

import io.github.bbodin.yncgamelab.models.gems.GemGrid;
import io.github.bbodin.yncgamelab.utils.Int2;

public class GemSolver {

    public static void solve (GemGrid grid) {

        grid.Move(new Int2(1,2), new Int2(1,0));
        grid.Move(new Int2(3,1), new Int2(1,0));
        grid.Move(new Int2(1,0), new Int2(1,0));
        grid.Move(new Int2(1,3), new Int2(1,0));

    }

}