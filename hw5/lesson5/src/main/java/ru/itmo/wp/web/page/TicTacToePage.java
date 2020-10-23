package ru.itmo.wp.web.page;

import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@SuppressWarnings({"unused", "RedundantSuppression"})
public class TicTacToePage {
    private void action(HttpServletRequest request, Map<String, Object> view) {
        if (request.getSession().getAttribute("state") == null) {
            newGame(request, view);
        }
        view.put("state", request.getSession().getAttribute("state"));
    }

    private void newGame(HttpServletRequest request, Map<String, Object> view) {
        State state = new State();
        request.getSession().setAttribute("state", state);
        view.put("state", state);
    }

    private void onMove(HttpServletRequest request, Map<String, Object> view) {
        HttpSession session = request.getSession();
        State state = (State) session.getAttribute("state");
        int row = 0;
        int col = 0;
        for (String parameter : request.getParameterMap().keySet()) {
            if (parameter.startsWith("cell_")) {
                row = parameter.charAt(5) - '0';
                col = parameter.charAt(6) - '0';
            }
        }

        state.setCell(row, col);
        view.put("state", state);
        session.setAttribute("state", state);
        throw new RedirectException("/TicTacToe");
    }

    public static class State {
        private final int size = 3;
        private final Character[][] cells = {{null, null, null}, {null, null, null}, {null, null, null}};
        private String phase;
        private boolean crossesMove;
        private int emptyCells = size * size;

        public State() {
            phase = "RUNNING";
            crossesMove = true;
        }

        public int getSize() {
            return size;
        }

        public Character[][] getCells() {
            return cells;
        }

        public String getPhase() {
            return phase;
        }

        public boolean isCrossesMove() {
            return crossesMove;
        }

        private Character turn() {
            return crossesMove ? 'X' : 'O';
        }

        private boolean isBelongedBoard(int x, int y) {
            return x >= 0 && x < size && y >= 0 && y < size;
        }

        private boolean check(int x, int y) {
            return (isBelongedBoard(x, y) && cells[x][y] == turn());
        }

        private int inLine(int startX, int startY, int stepX, int stepY) {
            int cnt = 0;
            int x = startX + stepX;
            int y = startY + stepY;
            for (int i = 0; i < size; i++) {
                if (!check(x, y)) {
                    break;
                }
                cnt++;
                x += stepX;
                y += stepY;
            }

            return cnt;
        }

        private boolean checkResult(int startX, int startY, int dX, int dY) {
            return inLine(startX, startY, dX, dY) + inLine(startX, startY, -dX, -dY) + 1 >= size;
        }

        public void setCell(int row, int col) {
            if (!phase.equals("RUNNING") || !isBelongedBoard(row, col) || cells[row][col] != null) {
                return;
            }
            cells[row][col] = turn();
            emptyCells--;
            if (checkResult(row, col, 1, 0) || checkResult(row, col, 0, 1)
                    || checkResult(row, col, 1, 1) || checkResult(row, col, 1, -1)) {
                phase = "WON_" + turn();
            }

            if (emptyCells == 0) {
                phase = "DRAW";
            }

            crossesMove = !crossesMove;
        }
    }
}
