package controller;

import java.net.URL;
import java.sql.Date;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.registerCourse;
import model.subjects;
import javafx.util.Callback;
public class control_courseRegis implements Initializable {
	@FXML
	private TextField txtsearch;

	@FXML
	private Label username;
	
	@FXML
	private Button btndel;

	@FXML
	private TableView<subjects> table_Search;

	@FXML
	private TableColumn<subjects, String> subcodecol;

	@FXML
	private TableColumn<subjects, String> namecol;

	@FXML
	private TableColumn<subjects, String> classcodecol;

	@FXML
	private TableColumn<subjects, String> numcol;
	
	@FXML
    private TableView<registerCourse> table_waitRegister;

    @FXML
    private TableColumn<registerCourse, String> registMamhCol;

    @FXML
    private TableColumn<registerCourse, String> regisTenmhCol;

    @FXML
    private TableColumn<registerCourse, String> regisMalopCol;

    @FXML
    private TableColumn<registerCourse, String> regisSotcCol;

    @FXML
    private TableColumn<String, String> regisTrangthaiCol;
    
	public static String user;
	public static String classcode;
	public ObservableList<subjects> subjectList; // danh sách tất cả môn học
	public ObservableList<subjects> listregist=FXCollections.observableArrayList(); // môn học được tìm thấy
	public ObservableList<subjects> listclasscode = FXCollections.observableArrayList(); // danh sách môn học theo mã
																							// lớp
	public ObservableList<registerCourse> waitlistregister = FXCollections.observableArrayList();// danh sách môn học đã
																									// đăng kí
	private static String mamhDel="",masvDel="";

	// tạo đối tượng, sử dụng equals để tìm kiếm 1 môn học
	@FXML
	boolean btn_search() {
		listregist.clear();
		if (!(txtsearch.getText().equals(""))) {
			String search = txtsearch.getText();
			subjects obj = new subjects(search);
			for (int i = 0; i < subjectList.size(); i++) {
				if (obj.equals(subjectList.get(i))) {
					String mmh = subjectList.get(i).getMalop();
					String tenmh = subjectList.get(i).getTenMonHoc();
					int sotc = subjectList.get(i).getSoTinChi();
					listregist.add(new subjects(search, tenmh, mmh, sotc));
				}
			}
			table_Search.setItems(listregist);
			return true;
		}
		return false;
	}

	// làm mới lại bảng sau khi tìm kiếm môn học
	@FXML
	private void refreshTable() {
		txtsearch.setText("");
		loadDate();
	}

	/*
	 * ở bảng này nếu dữ liệu được load từ CSDL thì sẽ giới hạn môn học phải là môn
	 * học theo mã lớp - mã lớp sẽ được lấy từ tài khoản đăng nhập của người dùng
	 * môn học phải được mở cho học kì này
	 */
	public void loadDate() {
		subjectList = FXCollections.observableArrayList(
				new subjects("TH02016", "Cấu trúc dữ liệu giải thuật", "K63ATTT", 3),
				new subjects("TH03016", "TH Cấu trúc dữ liệu giải thuật", "K63ATTT", 1),
				new subjects("TH03111", "Lập trình Java", "K63ATTT", 3),
				new subjects("KT03123", "Kinh tế nông nghiệp", "K63KTA", 3),
				new subjects("KT03132", "kinh tế vĩ mô", "K63KTA", 3),
				new subjects("TH02131", "Hệ quản trị CSDL", "K63HTTT", 3),
				new subjects("TH02131", "Hệ quản trị CSDL", "K63CNPM", 3)
				);
		table_Search.setItems(listclasscode);
	}

	/* SET danh sách môn học theo mã lớp */
	public void loadListClassCode() {
		for (int i = 0; i < subjectList.size(); i++) {
			if (subjectList.get(i).getMalop().equals(classcode)) {
				listclasscode.add(new subjects(subjectList.get(i).getMaMonHoc(), subjectList.get(i).getTenMonHoc(),
						subjectList.get(i).getMalop(), subjectList.get(i).getSoTinChi()));
			}
		}
	}

	// double click để hiện lên thông báo
	void doubleClick() {
		table_Search.setRowFactory(tv -> {
			TableRow<subjects> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (event.getClickCount() == 2 && (!row.isEmpty())) {
					int kt = 0;
					int ktmalop = 0;
					for(int i=0; i<subjectList.size(); i++) {
						if(subjectList.get(i).getMaMonHoc().equals(row.getItem().getMaMonHoc())
							&& subjectList.get(i).getMalop().equals(classcode)) {
							ktmalop = 1;
						}
					}
					if (AlertNotice(row.getItem().getTenMonHoc()) == true) {
						for (int i = 0; i < waitlistregister.size(); i++) {
							if ((!(row.getItem().getMaMonHoc().equals(waitlistregister.get(i).getMaMonHoc()))
									&& !(user.equals(waitlistregister.get(i).getMasv())))
									|| (!(row.getItem().getMaMonHoc().equals(waitlistregister.get(i).getMaMonHoc()))
											&& (user.equals(waitlistregister.get(i).getMasv())))
									|| ((row.getItem().getMaMonHoc().equals(waitlistregister.get(i).getMaMonHoc()))
											&& !(user.equals(waitlistregister.get(i).getMasv())))) {
								kt = 1;
							} else {
								kt = 0;
								Alert alert = new Alert(Alert.AlertType.INFORMATION);
								alert.setContentText("môn học đã được đăng kí");
								alert.show();
								break;
							}
						}
					}
					if ((kt == 1 && ktmalop ==1)|| (waitlistregister.size() == 0 && ktmalop == 1)) {
						waitlistregister
								.add(new registerCourse(row.getItem().getMaMonHoc(), row.getItem().getTenMonHoc(),
										row.getItem().getMalop(), row.getItem().getSoTinChi(), user));
						
						loadregister(); // SAU KHI LƯU THÌ SẼ LOAD LẠI BẢNG REGISTER
					}
					if(ktmalop != 1) {
						Alert alert = new Alert(Alert.AlertType.INFORMATION);
						alert.setContentText("MÔN HỌC KHÔNG CÓ TRONG TIẾN TRÌNH ĐÀO TẠO");
						alert.show();
					}
					
				}
			});
			return row;
		});
	}
	// DANH SÁCH MÔN HỌC ĐÃ CHỌN
	void loadregister() {
		table_waitRegister.setItems(waitlistregister);
		registMamhCol.setCellValueFactory(new PropertyValueFactory<>("maMonHoc"));
		regisTenmhCol.setCellValueFactory(new PropertyValueFactory<>("tenMonHoc"));
		regisMalopCol.setCellValueFactory(new PropertyValueFactory<>("malop"));
		regisSotcCol.setCellValueFactory(new PropertyValueFactory<>("soTinChi"));
	}
	// delete môn học
	@FXML
    void deleteCourse(ActionEvent event) {
		if(event.getSource() == btndel && mamhDel.equals("")) {
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setContentText("chưa có môn học nào được chọn");
			alert.show();
		}
		if(event.getSource() == btndel && !(mamhDel.equals("")) && !(masvDel.equals(""))) {
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setContentText("hủy môn học này khỏi danh sách đăng kí?");
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
				for(int i=0; i<waitlistregister.size(); i++) {
					if(waitlistregister.get(i).getMaMonHoc().equals(mamhDel) &&
					   waitlistregister.get(i).getMasv().equals(masvDel)) {
							waitlistregister.remove(i);
							mamhDel = "";	// sau khi xóa sẽ trả lại giá trị = null
							masvDel = "";
					}
				}
			}
			loadregister();// load lại bảng table_waitRegister sau khi hủy môn học
		}
    }
	// nhận thông tin môn học muốn hủy
	void clickRowCourseTable() {
		table_waitRegister.setRowFactory(tv -> {
			TableRow<registerCourse> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (event.getClickCount() == 1 && (!row.isEmpty())) {
					mamhDel = row.getItem().getMaMonHoc();
					masvDel = row.getItem().getMasv();
				}
			});
			return row;
		});
	}
	
	boolean AlertNotice(String tenmh) {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setContentText("thêm môn " + tenmh + " vào danh sách đăng kí?");
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		username.setText(user);
		loadDate();// GỌI HÀM loadDate trước để set tổng các môn học
		loadListClassCode(); // gọi hàm loadListClassCode để set array các môn học theo mã lớp
		if (btn_search() == true) {
			btn_search();
		}
		if (btn_search() == false) {
			loadDate();
		}
		refreshTable();
		subcodecol.setCellValueFactory(new PropertyValueFactory<>("maMonHoc"));
		namecol.setCellValueFactory(new PropertyValueFactory<>("tenMonHoc"));
		classcodecol.setCellValueFactory(new PropertyValueFactory<>("malop"));
		numcol.setCellValueFactory(new PropertyValueFactory<>("soTinChi"));
		doubleClick();
		clickRowCourseTable();
	}

}
