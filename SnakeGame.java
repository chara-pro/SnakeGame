import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.LinkedList;/**
* @author aachen0
* @date 2018/3/27 13:56
* IDE:IntelliJ IDEA
*/
public class SnakeGame {
static final int WIDTH = 20, HEIGHT = 5;
static char[][] map = new char[HEIGHT][WIDTH];

public static void main(String[] args) {
SnakeGame snakeGame = new SnakeGame();
snakeGame.initBackground();//��ʼ����������ֻ����
SnakeLine snakeLine = new SnakeLine();
snakeLine.initSnake();//��ʼ��һ����
snakeGame.putSnakeInMap(snakeLine);
snakeGame.show();//��ʾһ��
JFrame control = new JFrame("����̨,���̷����������ǰ���ķ���");
control.setBounds(900, 500, 100, 100);
control.setResizable(false);
control.setVisible(true);
control.addKeyListener(new KeyAdapter() {
@Override
public void keyPressed(KeyEvent e) {

switch (e.getKeyCode()) {
case KeyEvent.VK_UP:
snakeLine.direction=snakeLine.UP;
break;
case KeyEvent.VK_DOWN:
snakeLine.direction=snakeLine.DOWN;
break;

case KeyEvent.VK_LEFT:
snakeLine.direction=snakeLine.LEFT;
break;

case KeyEvent.VK_RIGHT:
snakeLine.direction=snakeLine.RIGHT;
break;
default:
break;
}
int result=snakeLine.move();
if (result == -1) {
snakeGame.putGameOverInMap(snakeLine.snakePoints.size());
snakeGame.show();
}

snakeGame.putSnakeInMap(snakeLine);
System.out.println("��ǰ�÷֣�"+(snakeLine.snakePoints.size()-3)*10);
snakeGame.show();

}
});

}

//���ַ�������
private void initBackground() {

for (int i = 0; i < HEIGHT; i++) {//��Χ������
for (int j = 0; j < WIDTH; j++) {//��ѭ�����Ƹ��еĵڼ���
this.map[i][j] = (j == 0 || (j == WIDTH - 1) || i == 0 || (i == HEIGHT - 1)) ? 'O' : ' ';
}
}

}

//��ʾ����
public void show() {
int height = map.length;
int width = map[0].length;
for (int i = 0; i < height; i++) {
for (int j = 0; j < width; j++) {
System.out.print(map[i][j]);
}
System.out.println();
}
}

//�Ѽӵ���ͼ
void putSnakeInMap(SnakeLine snakeLine) {
Point p;
this.initBackground();
map[SnakeLine.food.y][SnakeLine.food.x] = SnakeLine.worm;
for (int i = 0; i < snakeLine.snakePoints.size(); i++) {
p = snakeLine.snakePoints.get(i);
if (p.y > 0 && p.y < HEIGHT - 1 && p.x > 0 && p.x < WIDTH - 1) {
map[p.y][p.x] = (i == 0) ? snakeLine.head : snakeLine.body;
} else {
putGameOverInMap(snakeLine.snakePoints.size());
}
}
}

void putGameOverInMap(int points) {

char[] gameOver = ("GameOver Score:" + (points - 3)*10).toCharArray();//������18+�ַ�������̰���ߵĵ�ͼ���WIDTH����С��20
for (int i = 0; i < gameOver.length; i++) {
map[HEIGHT / 2 - 1][i + (WIDTH - gameOver.length) / 2] = gameOver[i];
}
show();
System.exit(1);
}

}class SnakeLine {
static final int RIGHT = 0, DOWN = 1, LEFT = 2, UP = 3;
static final char head = '+', body = '0', worm = '*';//ͷ�������ʾ
int direction;

static Point food = new Point((int) (Math.random() * (SnakeGame.WIDTH - 2)) + 1, (int) (Math.random() * (SnakeGame.HEIGHT - 2)) + 1);

private void newFood() {
int count=0;

food = new Point((int) (Math.random() * (SnakeGame.WIDTH - 2)) + 1, (int) (Math.random() * (SnakeGame
.HEIGHT - 2)) + 1);
if(snakePoints.contains(food)) {if(count>10){System.out.println("��Ӯ�ˣ�С����û������");return;}else {count++;newFood();}}
}

LinkedList<Point> snakePoints = new LinkedList<>();//�ߵ���������

void initSnake() {
Point head = new Point(SnakeGame.WIDTH / 2, SnakeGame.HEIGHT / 2);
snakePoints.addFirst(head);//ͷ
snakePoints.addLast(new Point(head.x - 1, head.y));
snakePoints.addLast(new Point(head.x - 2, head.y));
}

int move() {
Point head = snakePoints.getFirst();
Point newHead = null;
switch (direction) {
case RIGHT:
newHead = new Point(head.x + 1, head.y);
break;
case LEFT:
newHead = new Point(head.x - 1, head.y);
break;
case DOWN:
newHead = new Point(head.x, head.y + 1);
break;
case UP:
newHead = new Point(head.x, head.y - 1);
break;
}
if (snakePoints.contains(newHead)) {
return -1;//ҧ���Լ���
}
snakePoints.addFirst(newHead);
if (newHead.equals(food)) {//�Ե�ʳ����
newFood();
return 2;
}
snakePoints.removeLast();

return 1;
}
}
