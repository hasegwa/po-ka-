package ポーカー;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.Panel;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

class Kadai1216 extends Frame implements ActionListener{
    MenuBar menuBar;
    Menu fileMenu;
    Button adviceButton, exitButton;
    Image snowman;
    boolean comments = false;
    public Kadai1216(){
	super("Kadai1216");
	// 背景(得点表と雪だるま ^^;)
	snowman = Toolkit.getDefaultToolkit().getImage("snowman.png");
	// メニュー
	menuBar = new MenuBar();
	fileMenu = new Menu("File");
	fileMenu.add("Exit");
	fileMenu.addActionListener(this);
	menuBar.add(fileMenu);
	setMenuBar(menuBar);
	Panel panel = new Panel();
	panel.setLayout(new GridBagLayout());
	// ボタン
	panel.add(adviceButton = new Button("アドバイスを聞く"));
	adviceButton.addActionListener(this);
	panel.add(exitButton = new Button("Exit"));
	exitButton.addActionListener(this);
	setLayout(new BorderLayout());
	add(panel, "South");

	setSize(600,450);
	setResizable(false);
	setVisible(true);
    }
    public void actionPerformed(ActionEvent e){
	Object source = e.getSource();
	if(source.equals(adviceButton)){
	    comments = true;
	    repaint();
	}
	else if(source.equals(exitButton)){
	    System.out.println("終了します. お疲れさまでした.");
	    System.exit(0);
	}
	else if(source.equals(fileMenu)){
	    String command = e.getActionCommand();
	    if(command.equals("Exit")){
		System.out.println("終了します. お疲れさまでした.");
		System.exit(0);
	    }
	}
    }
    public void paint(Graphics g){
	Graphics2D g2=(Graphics2D)g;
	g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);

	g2.drawImage(snowman, 0, 50, this);

	// ボタンが押されたら
	if(comments){
	    drawComments(g2);
	}
    }
    // コンピューターの思考(予定)
    void drawComments(Graphics2D g2){
	g2.setColor(Color.white);
	g2.fillRoundRect(240,150,190,100,5,5);
	g2.setColor(Color.black);
	g2.drawRoundRect(240,150,190,100,5,5);
	g2.setColor(Color.white);
	int[] xs = {430, 440, 430};
	int[] ys = {200, 210, 207};
	g2.fillPolygon(xs, ys, 3);
	g2.setColor(Color.black);
	g2.drawLine(430, 200, 440, 210);
	g2.drawLine(430, 207, 440, 210);

	g2.setFont(new Font("Courier", Font.ITALIC, 12));
	g2.drawString("ごめんなさい。今日はまだ準備中です。",250,160);
    }
    public static void main(String [] args) throws IOException{
	Kadai1216 frame = new Kadai1216();
	Random r = new Random();
	int x[] = new int[5];
	int times = 0;
	while(true){
	    // カードの交換時に初期化したい変数は while 文より下に書く
	    int H[] = new int[15];
	    int C[] = new int[15];
	    int D[] = new int[15];
	    int S[] = new int[15];
	    int N[] = new int[15];
	    String Card[] = new String[5];
	    String ans, state;
	    int i, j, n = 0, Hn = 0, Cn = 0, Dn = 0, Sn = 0, Rn = 0;
	    int pair = 0, kind = 0, num = 0;
	    boolean Straight = false, Flush = false, Royal = false;
	    int Change[] = new int[5];
	    int y[] = new int[5];
	    // ２回改行
	    for(i = 0; i < 2; i++) System.out.println("");

	    // カード交換時は配らない
	    if(times == 0){
		// カードを５枚配る
		for(i = 0; i < 5; i++){
		    x[i] = r.nextInt(52)+1;
		}
		// 同じカードが一度に配られないようにする
		while(x[1] == x[0]){
		    x[1] = r.nextInt(52)+1;
		}
		while(x[2] == x[0] | x[2] == x[1]){
		    x[2] = r.nextInt(52)+1;
		}
		while(x[3] == x[0] | x[3] == x[1] | x[3] == x[2]){
		    x[3] = r.nextInt(52)+1;
		}
		while(x[4] == x[0] | x[4] == x[1] | x[4] == x[2] | x[4] == x[3]){
		    x[4] = r.nextInt(52)+1;
		}
	    }
	    for(i = 0; i < 5; i++){
		if(x[i] < 14){
		    H[x[i]] = 1;
		}
		else if(x[i] < 27){
		    C[x[i]-13] = 1;
		}
		else if(x[i] < 40){
		    D[x[i]-26] = 1;
		}
		else{
		    S[x[i]-39] = 1;
		}
	    }

	    // カードを, 2,3,4,5,6,7,8,9,10,J,Q,K,A の順,
	    // 番号が同じときはハート,クラブ,ダイヤ,スペードの順にソートする
	    for(i = 2; i < 11; i++){
		if(H[i] == 1){
		    Card[n] = "H-"+i;
		    n++;
		}
		if(C[i] == 1){
		    Card[n] = "C-"+i;
		    n++;
		}
		if(D[i] == 1){
		    Card[n] = "D-"+i;
		    n++;
		}
		if(S[i] == 1){
		    Card[n] = "S-"+i;
		    n++;
		}
	    }
	    if(H[11] == 1){
		Card[n] = "H-J";
		n++;
	    }
	    if(C[11] == 1){
		Card[n] = "C-J";
		n++;
	    }
	    if(D[11] == 1){
		Card[n] = "D-J";
		n++;
	    }
	    if(S[11] == 1){
		Card[n] = "S-J";
		n++;
	    }
	    if(H[12] == 1){
		Card[n] = "H-Q";
		n++;
	    }
	    if(C[12] == 1){
		Card[n] = "C-Q";
		n++;
	    }
	    if(D[12] == 1){
		Card[n] = "D-Q";
		n++;
	    }
	    if(S[12] == 1){
		Card[n] = "S-Q";
		n++;
	    }
	    if(H[13] == 1){
		Card[n] = "H-K";
		n++;
	    }
	    if(C[13] == 1){
		Card[n] = "C-K";
		n++;
	    }
	    if(D[13] == 1){
		Card[n] = "D-K";
		n++;
	    }
	    if(S[13] == 1){
		Card[n] = "S-K";
		n++;
	    }
	    if(H[1] == 1){
		Card[n] = "H-A";
		n++;
	    }
	    if(C[1] == 1){
		Card[n] = "C-A";
		n++;
	    }
	    if(D[1] == 1){
		Card[n] = "D-A";
		n++;
	    }
	    if(S[1] == 1){
		Card[n] = "S-A";
		n++;
	    }
	    for(i = 0; i < 4; i++){
		System.out.print(" "+(i+1)+"  ");
		// "10" は２文字なので幅を１マス大きくとる
		if(Card[i].lastIndexOf("0") == 3){
		    System.out.print(" ");
		}
	    }
	    System.out.println(" 5");
	    for(i = 0; i < 4; i++){
		System.out.print(Card[i]+" ");
	    }
	    System.out.println(Card[4]);

	    // 改行
	    for(i = 0; i < 1; i++) System.out.println("");

	    // カードを交換する準備をしておく
	    // 交換する前に一旦カードをソートしておく
	    for(i = 0; i < 5; i++){
		if(Card[i].substring(2).equals("A")){
		    Card[i] = Card[i].substring(0, 2)+"1";
		}
		if(Card[i].substring(2).equals("J")){
		    Card[i] = Card[i].substring(0, 2)+"11";
		}
		if(Card[i].substring(2).equals("Q")){
		    Card[i] = Card[i].substring(0, 2)+"12";
		}
		if(Card[i].substring(2).equals("K")){
		    Card[i] = Card[i].substring(0, 2)+"13";
		}

	    }
	    for(i = 0; i < 5; i++){
		if(Card[i].indexOf("H") == 0){
		    y[i] = Integer.parseInt(Card[i].substring(2));
		}
		else if(Card[i].indexOf("C") == 0){
		    y[i] = Integer.parseInt(Card[i].substring(2))+13;
		}
		else if(Card[i].indexOf("D") == 0){
		    y[i] = Integer.parseInt(Card[i].substring(2))+26;
		}
		else{
		    y[i] = Integer.parseInt(Card[i].substring(2))+39;
		}
	    }

	    // A,2,3,4,5 の組み合わせもストレートになるので,
	    // 番号 1 を番号 14 に仮想的に割り当てる
	    H[14] = H[1];
	    C[14] = C[1];
	    D[14] = D[1];
	    S[14] = S[1];

	    // 番号 i のカードの枚数の合計を N[i] とし, ストレートの判定等に使う
	    for(i = 1; i < 15; i++){
		N[i] = H[i]+C[i]+D[i]+S[i];
	    }

	    // ストレート(Straight : ５枚の番号が連続)の判定
	    for(i = 1; i < 11; i++){
		if(N[i] == 1 & N[i+1] == 1 & N[i+2] == 1 & N[i+3] == 1 & N[i+4] == 1){
		    Straight = true;
		}
	    }
	    // フラッシュ(Flush : すべて同じ柄)の判定
	    for(i = 1; i < 14; i++){
		if(H[i] == 1) Hn++;
		if(C[i] == 1) Cn++;
		if(D[i] == 1) Dn++;
		if(S[i] == 1) Sn++;
	    }
	    if(Hn == 5 | Cn == 5 | Dn == 5 | Sn == 5){
		Flush = true;
	    }
	    // ロイヤル(Royal : 10,J,Q,K,A のみからなる)の判定
	    for(i = 10; i< 15; i++){
		Rn = Rn+H[i]+C[i]+D[i]+S[i];
	    }
	    if(Rn == 5){
		Royal = true;
	    }

	    // フォーカード, スリーカード, ペアの判定
	    for(i = 1; i < 14; i++){
		if(N[i] == 4){
		    kind = 4;
		}
		else if(N[i] == 3){
		    kind = 3;
		}
		else if(N[i] == 2){
		    pair++;
		}
	    }

	    // 役の判定
	    if(Royal & Straight & Flush){    // ロイヤル・ストレート・フラッシュ
		state = "rsf";
	    }
	    else if(Royal & Straight){       // ロイヤル・ストレート
		state = "rst";
	    }
	    else if(Straight & Flush){       // ストレート・フラッシュ
		state = "stf";
	    }
	    else if(Straight){               // ストレート
		state = "str";
	    }
	    else if(Flush){                  // フラッシュ
		state = "flu";
	    }
	    else if(kind == 4){              // フォーカード
		state = "4ok";
	    }
	    else if(kind == 3 & pair == 1){  // フルハウス
		state = "ful";
	    }
	    else if(kind == 3){              // スリーカード
		state = "3ok";
	    }
	    else if(pair == 2){              // ツーペア
		state = "2pa";
	    }
	    else if(pair == 1){              // ワンペア
		state = "1pa";
	    }
	    else{                            // ノーペア
		state = "nop";
	    }

	    // このあたりでコンピューターにより期待値の計算をさせて最適な交換を
	    // 求めようとしたが、失敗

	    BufferedReader d=new BufferedReader(new InputStreamReader(System.in));
	    if(times == 2){
		System.out.println("あなたのスコアは "+scoreCalc(state)+" 点です.");
	    }
	    else{
		while(true){
		    System.out.println("カードを交換しますか (y/n)?");
		    ans = d.readLine();
		    // カードを交換する場合
		    if(ans.equals("y")){
			System.out.println("交換するカードを選んでください.");
			System.out.print(" 1 番のカードを 1, 2 番のカードを 2,");
			System.out.println(" 3 番のカードを 4,");
			System.out.println(" 4 番のカードを 8, 5 番のカードを 16");
			System.out.println("とし, 交換するカードの合計を入力してください.");
			System.out.println("例：2 番と 4 番 を交換するなら, 10 と入力 (0-31)?");
			while(true){
			    ans = d.readLine();
			    try{
				num = Integer.parseInt(ans);
			    }
			    catch(java.lang.NumberFormatException e){
				System.out.println("数字を入力してください (0-31)?");
				continue;
			    }
			    if(num < 0 | num > 31){
				System.out.println("0 から 31 の数字を入力してください (0-31)?");
			    }
			    else{
				break;
			    }
			}
			// 入力された数字を分解してカード番号になおす
			n = 0;
			if(num > 15){
			    Change[n] = 5;
			    n++;
			    num = num-16;
			}
			if(num > 7){
			    Change[n] = 4;
			    n++;
			    num = num-8;
			}
			if(num > 3){
			    Change[n] = 3;
			    n++;
			    num = num-4;
			}
			if(num > 1){
			    Change[n] = 2;
			    n++;
			    num = num-2;
			}
			if(num == 1){
			    Change[n] = 1;
			    n++;
			}
			if(n == 0){
			    System.out.println("カードの交換は行ないません.");
			    System.out.println("あなたのスコアは "+scoreCalc(state)+" 点です.");
			    break;
			}
			else{
			    for(i = n; i > 0; i--){
				System.out.print("カード "+Change[i-1]+", ");
				// 新しいカード
				y[Change[i-1]-1] = r.nextInt(52)+1;
				// 以前出たカードは出ないようにする
				// さらに, 同じカードは一度に配られないようにする
				if(Change[i-1]-1 == 0){
				    while(y[0] == x[0] | y[0] == x[1] | y[0] == x[2] | y[0] == x[3] | y[0] == x[4]){
					y[0] = r.nextInt(52)+1;
				    }
				}
				if(Change[i-1]-1 == 1){
				    while(y[1] == y[0] |
					  y[1] == x[0] | y[1] == x[1] | y[1] == x[2] | y[1] == x[3] | y[1] == x[4]){
					y[1] = r.nextInt(52)+1;
					}
				}
				if(Change[i-1]-1 == 2){
				    while(y[2] == y[0] | y[2] == y[1] |
					  y[2] == x[0] | y[2] == x[1] | y[2] == x[2] | y[2] == x[3] | y[2] == x[4]){
					y[2] = r.nextInt(52)+1;
				    }
				}
				if(Change[i-1]-1 == 3){
				    while(y[3] == y[0] | y[3] == y[1] | y[3] == y[2] |
					  y[3] == x[0] | y[3] == x[1] | y[3] == x[2] | y[3] == x[3] | y[3] == x[4]){
					y[3] = r.nextInt(52)+1;
				    }
				}
				if(Change[i-1]-1 == 4){
				    while(y[4] == y[0] | y[4] == y[1] | y[4] == y[2] | y[4] == y[3] |
					  y[4] == x[0] | y[4] == x[1] | y[4] == x[2] | y[4] == x[3] | y[4] == x[4]){
					y[4] = r.nextInt(52)+1;
				    }
				}
			    }
			    System.out.println("を交換します.");
			    for(i = 0; i < 5; i++){
				x[i] = y[i];
			    }
			    times++;
			    break;
			}
		    }
		    // カードの交換を行なわない場合
		    else if(ans.equals("n")){
			System.out.println("あなたのスコアは "+scoreCalc(state)+" 点です.");
			break;
		    }
		    // それ以外の文字が入力されたとき
		    else{
			System.out.println("y または n を入力してください.");
		    }
		    continue;
		}
	    }
	    // カード交換を行なうときはもう一度ループ. もうカードは確定している.
	    if(times == 1){
		times++;
		continue;
	    }
	    // もう一度するのかしないのか
	    while(true){
		System.out.println("もう一度プレイしますか (y/n)?");
		ans = d.readLine();
		if(ans.equals("y")){
		    for(i = 0; i < 5; i++){
			x[i] = 0;
		    }
		    times = 0;
		    break;
		}
		else if(ans.equals("n")){
		    System.out.println("終了します. お疲れさまでした.");
		    System.exit(0);
		}
		else{
		    System.out.println("y または n を入力してください.");
		    continue;
		}
	    }
	}
    }
    // 判定(String)からスコア(Integer)を返す. コンピューターによる仮想的な判定や,
    // 対戦等, 複数の判定に対して使うつもりだった. 今の機能に対してはややオーバーか
    public static int scoreCalc(String s){
	if(s.equals("rsf")) return 2800000;
	else if(s.equals("stf")) return 310000;
	else if(s.equals("4ok")) return 18000;
	else if(s.equals("rst")) return 12000;
	else if(s.equals("ful")) return 3000;
	else if(s.equals("flu")) return 2200;
	else if(s.equals("str")) return 1200;
	else if(s.equals("3ok")) return 200;
	else if(s.equals("2pa")) return 90;
	else if(s.equals("1pa")) return 10;
	else return 0;
    }
}