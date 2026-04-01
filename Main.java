import java.util.*;
public class Main {
    static String[][] bingo = new String[5][5]; //bingoカード
    static boolean judge = false;               //重複判定

    //bingo作成メソッド
    public static void Duplication(int min, int column) {
        int bingo_value = (int)(Math.random() * 15 + min);
        for (int i=0; i<5; i++) {
            if (i != 0) {
                while (judge == false) {
                    bingo_value = (int)(Math.random() * 15 + min);
                    for (int j=0; j<i; j++) {
                        if (bingo_value == Integer.parseInt(bingo[j][column])) {
                            judge = false;
                            break;
                        } else {
                            judge = true;
                        }
                    }
                }
            }
            judge = false;
            bingo[i][column] = String.valueOf(bingo_value);
        }
    }

    //メイン処理
    public static void main(String[] args) {
        int ball = 0;                                               //ボール番号
        ArrayList<Integer> ball_list = new ArrayList<Integer>();    //bingo値の保管用
        int reach_count = 0; //リーチの数
        int bingo_count = 0; //ビンゴの数
        int count = 0; //穴数える
        int number = 1;

        for (int i=0; i<5; i++) {
            Duplication(1+(i*15), i);                               //bingo作成メソッド起動
        }
        bingo[2][2] = "FREE"; //真ん中を穴あき状態にする

        //ビンゴが全部開くまで続ける
        while (bingo_count != 12) {
            reach_count = 0;
            bingo_count = 0;
            //ballの取得
            while (judge == false) {
                ball = (int)(Math.random() * 75 + 1);
                if (ball_list.size() != 0) {
                    for (int i=0; i<ball_list.size(); i++) {
                        if (ball == ball_list.get(i)) {
                            judge = false;
                            break;
                        } else {
                            judge = true;
                        }
                    }
                } else {
                    judge = true;
                }
            }
            judge = false;
            ball_list.add(ball);

            //穴あき判定
            for (int i=0; i<5; i++) {
                for (int j=0; j<5; j++) {
                    //真ん中と既に開いたところを除外
                    if (!bingo[i][j].equals("FREE") && bingo[i][j].charAt(0) !='(') {
                        //ビンゴカードに番号があったら
                        if (ball == Integer.parseInt(bingo[i][j])) {
                            if (bingo[i][j].length() == 1) {
                                bingo[i][j] = "0"+bingo[i][j];
                            }
                            bingo[i][j] = "("+bingo[i][j]+")";
                        }
                    }
                }
            }

            //縦ビンゴ処理
            for (int i=0; i<5; i++) {
                count = 0;
                for (int j=0; j<5; j++) {
                    if (bingo[j][i].charAt(0) == '(' || bingo[j][i].equals("FREE")) {
                        count++;
                    }
                }
                if (count == 5) {
                    bingo_count++;
                } else if (count == 4) {
                    reach_count++;
                }
            }

            //横ビンゴ処理
            for (int i=0; i<5; i++) {
                count = 0;
                for (int j=0; j<5; j++) {
                    if (bingo[i][j].charAt(0) == '(' || bingo[i][j].equals("FREE")) {
                        count++;
                    }
                }
                if (count == 5) {
                    bingo_count++;
                } else if (count == 4) {
                    reach_count++;
                }
            }

            //斜め処理(左上から右下)
            count = 0;
            for (int i=0; i<5; i++) {
                if (bingo[i][i].charAt(0) == '(' || bingo[i][i].equals("FREE")) {
                    count++;
                }
            }
            if (count == 5) {
                bingo_count++;
            } else if (count == 4) {
                reach_count++;
            }

            //斜め処理(左下から右上)
            count = 0;
            for (int i=0; i<5; i++) {
                if (bingo[4-i][i].charAt(0) == '(' || bingo[4-i][i].equals("FREE")) {
                    count++;
                }
            }
            if (count == 5) {
                bingo_count++;
            } else if (count == 4) {
                reach_count++;
            }

            //出力
            System.out.println("ball[" + number + "]:" + ball);
            for (int i=0; i<5; i++) {
                for (int j=0; j<5; j++) {
                    if (bingo[i][j].charAt(0) == '(' || bingo[i][j].equals("FREE")) {
                        System.out.print(bingo[i][j]);
                    } else {
                        if (bingo[i][j].length() == 1) {
                            System.out.print(" 0" + bingo[i][j] + " ");
                        }
                        else {
                            System.out.print(" " + bingo[i][j] + " ");
                        }
                    }
                }
                System.out.println();
            }
            System.out.println();
            System.out.println("REACH: " + reach_count);
            System.out.println("BINGO: " + bingo_count);
            System.out.println("--------------------");
            number++;
        }
    }
}