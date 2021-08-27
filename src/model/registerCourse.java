package model;

public class registerCourse extends subjects{
	
	private String masv;
	public registerCourse(String maMonHoc, String tenMonHoc, String malop, int soTinChi) {
		super(maMonHoc, tenMonHoc, malop, soTinChi);
	}
	public registerCourse(String maMonHoc, String tenMonHoc, String malop, int soTinChi, String masv) {
		super(maMonHoc, tenMonHoc, malop, soTinChi);
		this.masv = masv;
	}
	public String getMasv() {
		return masv;
	}
	public void setMasv(String masv) {
		this.masv = masv;
	}
	@Override
	public String toString() {
		return "mamh: "+getMaMonHoc()+" ten mon hoc: "+getTenMonHoc() +" MA LOP" + getMalop();
	}

}