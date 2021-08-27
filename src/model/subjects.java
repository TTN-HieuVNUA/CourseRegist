package model;

public class subjects {
	private String maMonHoc;
	private String tenMonHoc;
	private String malop;
	private int soTinChi;
	public subjects(String maMonHoc, String tenMonHoc, String malop, int soTinChi) {
		super();
		this.maMonHoc = maMonHoc;
		this.tenMonHoc = tenMonHoc;
		this.malop = malop;
		this.soTinChi = soTinChi;
	}
	public subjects(String maMonHoc) {
		this.maMonHoc = maMonHoc;
	}
	public subjects(String malop, String mamh) {
		this.maMonHoc = mamh;
		this.malop = malop;
	}
	public String getMaMonHoc() {
		return maMonHoc;
	}
	public void setMaMonHoc(String maMonHoc) {
		this.maMonHoc = maMonHoc;
	}
	public String getTenMonHoc() {
		return tenMonHoc;
	}
	public void setTenMonHoc(String tenMonHoc) {
		this.tenMonHoc = tenMonHoc;
	}
	public String getMalop() {
		return malop;
	}
	public void setMalop(String malop) {
		this.malop = malop;
	}
	public int getSoTinChi() {
		return soTinChi;
	}
	public void setSoTinChi(int soTinChi) {
		this.soTinChi = soTinChi;
	}
	@Override
	public boolean equals(Object obj) {
		subjects another = (subjects)obj;
		if(this.getMaMonHoc().equals(another.maMonHoc)) {
			return true;
		}
		return false;
	}
}
