// Hàm kiểm tra số điện thoại
function isValidPhoneNumber(phone) {
    const phoneRegex = /^(0|\+84)(3|5|7|8|9)[0-9]{8}$/;
    return phoneRegex.test(phone);
}

// Thêm validation cho form thêm mới
document.getElementById('addAddressForm').addEventListener('submit', function(e) {
    const sdt = document.getElementById('sdt').value;
    if (!isValidPhoneNumber(sdt)) {
        e.preventDefault();
        alert('Số điện thoại không hợp lệ. Vui lòng nhập số điện thoại đúng định dạng (VD: 0912345678 hoặc +84912345678)');
        return false;
    }
    return true;
});

// Thêm validation cho form sửa
document.getElementById('editAddressForm').addEventListener('submit', function(e) {
    const sdt = document.getElementById('editSdt').value;
    if (!isValidPhoneNumber(sdt)) {
        e.preventDefault();
        alert('Số điện thoại không hợp lệ. Vui lòng nhập số điện thoại đúng định dạng (VD: 0912345678 hoặc +84912345678)');
        return false;
    }
    return true;
});

function editAddress(btn) {
    const id = btn.getAttribute('data-id');
    // Tìm thẻ chứa thông tin địa chỉ theo class mới
    const card = btn.closest('.address-card-adv');
    if (!card) {
        alert('Không tìm thấy thông tin địa chỉ!');
        return;
    }
    // Lấy idNguoiDung từ dữ liệu Thymeleaf nếu có
    let idNguoiDung = '';
    let hoTen = '';
    let sdt = '';
    const nameDiv = card.querySelector('.address-name-adv');
    if (nameDiv) {
        hoTen = nameDiv.innerText.trim();
        if (hoTen.startsWith('ID:')) {
            hoTen = '';
        }
    }
    const phoneDiv = card.querySelector('.address-phone-adv span');
    if (phoneDiv) {
        sdt = phoneDiv.innerText.trim();
    }
    // Lấy địa chỉ chi tiết
    const diaChi = card.querySelector('.address-detail-adv span')?.innerText || '';
    // Lấy khu vực
    const khuVuc = card.querySelector('.address-district-adv')?.innerText || '';
    const [phuongXa, quanHuyen, thanhPho] = khuVuc.split(',').map(s => s.trim());
    // Lấy idNguoiDung từ thuộc tính Thymeleaf nếu có
    idNguoiDung = card.getAttribute('data-id-nguoi-dung') || '';
    // Lấy trạng thái mặc định
    const macDinh = card.hasAttribute('data-mac-dinh');
    // Đổ dữ liệu vào modal
    document.getElementById('editDiaChiId').value = id;
    document.getElementById('editNguoiDungId').value = idNguoiDung;
    document.getElementById('editHoTen').value = hoTen;
    document.getElementById('editSdt').value = sdt;
    document.getElementById('editDiaChi').value = diaChi;
    document.getElementById('editPhuongXa').value = phuongXa || '';
    document.getElementById('editQuanHuyen').value = quanHuyen || '';
    document.getElementById('editThanhPho').value = thanhPho || '';
    document.getElementById('editMacDinh').checked = macDinh;
    // Hiển thị modal
    const modal = new bootstrap.Modal(document.getElementById('editAddressModal'));
    modal.show();
}

