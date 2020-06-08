package nguyentrinhan70.example.com.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.mysql.jdbc.Driver;

public class TaiSanUI  extends JFrame{

	public static Connection conn = null;
	public static Statement statement = null;

	DefaultTableModel dtbTaiSan;
	JTable tblTaiSan;
	JButton btnThemMoi, btnSua;
	public TaiSanUI(String title){
		super(title);
		addControls();
		addEvents();
		ketNoiCSDLMySQL();
		hienThiToanBoTaiSan();
	}

	private void hienThiToanBoTaiSan() {
		// TODO Auto-generated method stub
		try{
			String sql = "select * from taisan";
			statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			dtbTaiSan.setRowCount(0);
			while(resultSet.next()){
				Vector<Object> vec = new Vector<Object> ();
				vec.add(resultSet.getString(1));
				vec.add(resultSet.getString(2));
				vec.add(resultSet.getDate(3));
				vec.add(resultSet.getInt(4));
				vec.add(resultSet.getInt(5));
				dtbTaiSan.addRow(vec);
			}

		}catch(Exception ex){
			ex.printStackTrace();
		}

	}

	private void ketNoiCSDLMySQL() {
		// TODO Auto-generated method stub
		try{
			String strConn = "jdbc:mysql://localhost/dbquanlytaisan?useUnicode=true&characterEncoding=utf-8";
			Properties pro = new Properties();
			pro.put("user", "root");
			pro.put("password", "");
			Driver driver = new Driver();
			conn = driver.connect(strConn, pro);

		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	private void addEvents() {
		// TODO Auto-generated method stub
		btnThemMoi.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				TaiSanChiTietUI ui = new TaiSanChiTietUI("Thông tin chi tiết");
				ui.showWindow();
				if(TaiSanChiTietUI.ketqua>0)
					hienThiToanBoTaiSan();

			}
		});
		btnSua.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int row = tblTaiSan.getSelectedRow();
				String ma = tblTaiSan.getValueAt(row, 0)+"";

				TaiSanChiTietUI ui = new TaiSanChiTietUI("Thông tin chi tiết");
				ui.maTSChon = ma;
				ui.hienThiThongTinChiTiet();
				ui.showWindow();
				if(TaiSanChiTietUI.ketqua>0)
					hienThiToanBoTaiSan();

			}
		});
	}

	private void addControls() {
		// TODO Auto-generated method stub
		Container con = getContentPane();
		con.setLayout(new BorderLayout());
		JPanel pnNorth = new JPanel();
		JLabel lblTitle = new JLabel("Chương trình quản lý tài sản");
		pnNorth.add(lblTitle);
		lblTitle.setFont(new Font("arial", Font.BOLD, 30));
		lblTitle.setForeground(Color.BLUE);
		con.add(pnNorth, BorderLayout.NORTH);

		dtbTaiSan = new DefaultTableModel();
		dtbTaiSan.addColumn("Mã tài sản");
		dtbTaiSan.addColumn("Tên tài sản");
		dtbTaiSan.addColumn("Ngày nhập");
		dtbTaiSan.addColumn("Khấu hao");
		dtbTaiSan.addColumn("Giá trị");
		tblTaiSan = new JTable(dtbTaiSan);
		JScrollPane scTable = new JScrollPane(tblTaiSan, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		con.add(scTable, BorderLayout.CENTER);

		JPanel pnButton = new JPanel();
		pnButton.setLayout(new FlowLayout(FlowLayout.LEFT));
		btnThemMoi = new JButton("Thêm mới");
		btnSua = new JButton("Sửa thông tin");

		pnButton.add(btnThemMoi);
		pnButton.add(btnSua);
		con.add(pnButton, BorderLayout.SOUTH);


	}
	public void showWindow(){
		this.setSize(600, 500);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setVisible(true);

	}

}
