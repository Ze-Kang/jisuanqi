package jisuanQi;
import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
public class Caculate {
	// 对计算器整体框架的建立
	private JFrame frame = new JFrame("&斌的图形界面计算器&");// 创建窗体对象
	private JLabel label_hint = new JLabel(); // 创建提示信息标签
	private JLabel label_input = new JLabel(); // 创建输入信息标签
	private JLabel label_result = new JLabel(); // 创建输出结果标签3
	private JPanel panel_show = new JPanel(); // 创建显示面板
	private JPanel panel_operation = new JPanel(); // 创建按键面板
 
	private String[] str = { "AC", "←", "(-)", "+", "7", "8", "9", "-", "4", "5", "6", "*", "1", "2", "3", "÷", "%",
			"0", ".", "=" }; // 计算器的按键从上到下、从左到右顺序表示
	private JButton[] button = new JButton[str.length]; // 创建计算器的按键对象
	private double result = 0; // 存储计算结果
	private boolean flag = true; //
	private boolean find = true; 
	private String command = "=";
 
	public static void main(String[] args) {// 主方法
		new Caculate().initJFrame();
	}
 
	// 对界面进行初始化
	public void initJFrame() {
		// 设置窗体位置及大小(下面一段代码实现居中)
		frame.setSize(600, 550); 
		frame.setLocationRelativeTo(null);//设置窗口相对于指定组件的位置。如果组件当前未显示，或者 c 为 null，则此窗口将置于屏幕的中央。
		label_hint.setText(" "); // 标签内容初始化为空
		label_hint.setHorizontalAlignment(JLabel.RIGHT); // 设置图标和文本的水平对齐方式为右对齐
		label_hint.setFont(new Font("宋体", Font.BOLD, 30));// 设置此标签的字体。
		label_hint.setForeground(Color.RED); // 设置字体颜色为红色
 
		label_input.setText(" "); // 标签内容初始化为空
		label_input.setHorizontalAlignment(JLabel.RIGHT); // 设置图标和文本的水平对齐方式为右对齐
		label_input.setFont(new Font("宋体", Font.BOLD, 30));// 设置此标签的字体。
		label_input.setForeground(Color.BLACK); // 设置字体颜色为黑色
 
		label_result.setText("0");// 标签内容初始化内容为0
		label_result.setHorizontalAlignment(JLabel.RIGHT); // 设置图标和文本的水平对齐方式为右对齐
		label_result.setFont(new Font("宋体", Font.BOLD, 35));// 设置此标签的字体。
		label_result.setForeground(Color.BLUE); // 设置字体颜色为蓝色
 
		panel_show.setLayout(new BorderLayout()); // 设置为边界布局
		panel_show.add(label_hint, BorderLayout.NORTH); // 把错误提示标签放在上面
		panel_show.add(label_input, BorderLayout.CENTER);// 把输入信息标签放在中间
		panel_show.add(label_result, BorderLayout.SOUTH);// 把输出结果标签放在下面
		// 创建具有指定类型、高亮显示和阴影颜色的斜面边框。
		panel_show.setBorder(
				new BevelBorder(BevelBorder.RAISED, new Color(160, 170, 180), null, SystemColor.scrollbar, null));
		panel_operation.setLayout(new GridLayout(5, 4, 8, 8));// 按键设置为网格布局5行5列间距为8
		// 创建具有指定类型、高亮显示和阴影颜色的斜面边框。凸出斜面类型。为滚动条提供的背景色。
		panel_operation.setBorder(
				new BevelBorder(BevelBorder.RAISED, new Color(160, 170, 180), null, SystemColor.scrollbar, null));
		// 添加按钮
		for (int i = 0; i < str.length; i++) {
			button[i] = new JButton(str[i]);
			button[i].setFont(new Font("宋体", Font.BOLD, 30)); // 设置按钮中字体样式
			if (str[i].equals("+") || str[i].equals("-") || str[i].equals("*") || str[i].equals("÷")
					|| str[i].equals("%") || str[i].equals("=")) {
				button[i].addActionListener(new OperationListener());// 给按钮+,-,*,/,%,=注册监听器
			} else {
				button[i].addActionListener(new DataListener());//给按钮AC,←,(-),.,0,1,2,3,4,5,6,7,8,9注册监听器
			}
 
			panel_operation.add(button[i]);// 把每个按钮分别添加到面板上
		}
		// 把面板添加到窗体框架里
		frame.add(panel_operation, BorderLayout.CENTER);
		frame.add(panel_show, BorderLayout.NORTH);
		//frame.setResizable(false); // 设置窗体不能改变大小
		frame.setVisible(true); // 设置窗体可见
	}
	
	public class DataListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String str = e.getActionCommand();//返回与此动作相关的命令字符串。
			if (Caculate.this.flag == true) {//当想输入新的数字时，清空输入显示标签
				label_input.setText(" ");
				Caculate.this.flag = false;
 
			}
			if (str.equals("(-)")) {//当点击(-)时
				if (label_input.getText().indexOf("(-)") == -1) {//如果此时显示框里还没出现（-）时
					label_input.setText(str);
				}
			}
			if (!str.equals("(-)")) {//当命令不是(-)时
				if (str.equals("AC")) { //点击清零键时
					clear();
				} else if (str.equals("←")) { //点击返回键时
					getBack();
				} else if (str.equals(".")) { // 点击小数点时，小数点只能出现一次，不能在首位
					if (label_input.getText().indexOf(".") == -1 && !label_input.getText().equals(" ")) {
						label_input.setText(label_input.getText() + str);
					} else {
						//clear();
						label_input.setText(label_input.getText() + str);
						label_hint.setText("小数点不合法，请重新输入");
					}
				} else { //点击0,1,2,3,4,5,6,7,8,9时显示原数
					label_input.setText(label_input.getText() + str);
				}
			}
		}
	}
 
	// 返回上一级
	public void getBack() {
		if (label_input.getText().length() <= 1) { //长度小于等于1时清空显示框
			clear();
		} else {
			String str = "";
			if (label_input.getText().length() == 3) { //处理(-)问题，我把(-)看成算一个字符，要返回就整体返回
				String str1 = label_input.getText().substring(label_input.getText().length() - 3);
				if (str1.equals("(-)")) {
					str = " ";
				} else {
					str = label_input.getText().substring(0, label_input.getText().length() - 1);
				}
			} else { //长度大于3时正常返回上一级
				str = label_input.getText().substring(0, label_input.getText().length() - 1);
			}
			label_input.setText(str); //设置输入显示框为str
		}
		label_hint.setText(" "); //清空提示显示框
	}
 
	// 清屏
	public void clear() {
		label_hint.setText(" ");
		label_input.setText(" ");
		label_result.setText("0");
		this.result = 0;
		Caculate.this.flag = true; //恢复初始化标记
		Caculate.this.find = true; //恢复初始化标记
	}
 
	public class OperationListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String str = e.getActionCommand(); // 返回与此动作相关的命令字符串
			if (Caculate.this.flag == false) { //当第一次点击+，-，*，、，%，=时
				if (label_input.getText().contains("(-)")) { //把(-)处理为-，然后在进行数据操作
					label_input.setText(label_input.getText().replaceAll("\\(-\\)", "-"));
				}
 
				caculate(Double.parseDouble(label_input.getText())); //计算
				Caculate.this.flag = true; //把flag标记处理点击多次操作运算符和输入下一个数的情况
				Caculate.this.command = str; //供下一次数据运算操作命令
			} else { //多次点击+,-,*,/,%,=时
				Caculate.this.command = str;
				if (str == "%" && Caculate.this.find == true) { //处理刚开始输入显示框为空时对0取余的情况
					Caculate.this.find = false; //把find标记
					Caculate.this.command = "=";
				}
			}
		}
	}
	//进行数据运算
	public void caculate(double ans) {
		switch (this.command) {
		case "+": //计算数据的和
			this.result += ans;
			break;
		case "-": //计算数据的差
			this.result -= ans;
			break;
		case "*": //计算数据的乘积
			this.result *= ans;
			break;
		case "÷": //计算数据的商
			if (ans == 0) {
				label_hint.setText("提示：除数不能为0");
				return;
			}
			this.result /= ans;
			break;
		case "%": //计算数据的余
			double s1 = this.result;
			int d1 = (int) s1;
			double s2 = ans;
			int d2 = (int) s2;
			if (d1 == this.result && d2 == ans && d2 != 0) {
				this.result %= ans;
			} else {
				if (d1 != s1) {
					label_hint.setText("提示：余数的分子必须为整数");
				}
				else if (d2 != s2) {
					label_hint.setText("提示：余数的分母必须为整数");
				}
				else if (s2 == 0) {
					label_hint.setText("提示：余数的分母不能为0");
				}
			}
			break;
		case "=": //计算结果
			this.result = ans;
			break;
		}
		label_result.setText(" " +(this.result)); //显示计算结果
	}
}