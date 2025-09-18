# Hệ thống Quản lý Thư viện (Library Management System)

Đây là một dự án Hệ thống Quản lý Thư viện đơn giản, dựa trên giao diện dòng lệnh (console-based) được viết bằng Kotlin. Hệ thống cho phép người dùng (Thành viên và Thủ thư) đăng nhập và thực hiện các chức năng cơ bản của thư viện. Dữ liệu của hệ thống được tải từ các tệp CSV khi khởi động.

## Tính năng

- **Xác thực người dùng**: Đăng nhập và đăng xuất cho các vai trò khác nhau (Thành viên, Thủ thư).
- **Tải dữ liệu từ CSV**: Tự động tải thông tin sách, thành viên, và thủ thư từ các tệp `.csv` trong thư mục `data`.
- **Tổ chức Danh mục**: Dữ liệu sách được sắp xếp vào các map để tối ưu hóa việc tra cứu.
- **Tìm kiếm sách**: Thành viên có thể tìm kiếm sách dựa trên:
  - Tiêu đề
  - Tác giả
  - Chủ đề
  - Nhà xuất bản
- **Phân quyền**: Hiển thị các menu chức năng khác nhau tùy thuộc vào vai trò của người dùng đã đăng nhập.

## Cấu trúc Dự án

- `LibraryManager.kt`: Lớp chính, điều khiển luồng hoạt động của ứng dụng, bao gồm đăng nhập, các menu chức năng trong một vòng lặp chính của chương trình.
- `data/`: Thư mục chứa các tệp dữ liệu nguồn ở định dạng CSV.
  - `book_items.csv`: Thông tin chi tiết về các cuốn sách.
  - `members.csv`: Thông tin tài khoản của thành viên.
  - `librarians.csv`: Thông tin tài khoản của thủ thư.
- `catalog/`
  - `Catalog.kt`: Một đối tượng singleton quản lý việc lưu trữ và tra cứu sách.
  - `Search.kt`: Interface định nghĩa các phương thức tìm kiếm.
- `book/`: Chứa các lớp dữ liệu liên quan đến sách, bao gồm `Book` (thông tin chung về sách), `BookItem` (một bản sao cụ thể của sách), `BookFormat` (định dạng sách như bìa cứng, bìa mềm), và `Rack` (vị trí kệ sách).
- `member/`: Chứa các lớp liên quan đến người dùng hệ thống, bao gồm `Member` (thành viên thư viện), `Librarian` (thủ thư), và interface `User` chung.
- `person/`: Chứa các lớp mô tả thông tin cá nhân như `Person`, `Author` (tác giả), và `Address` (địa chỉ).
- `business`: Chứa các lớp mô tả các thông tin của việc mượn sách, bao gồm `Loan` (mượn sách), `Reservation` (đặt trước), và `ReservationStatus` (trạng thái đặt)

## Cách chạy

1.  Mở dự án trong Android Studio.
2.  Tìm đến file chứa hàm `main` để khởi chạy ứng dụng.
3.  Một giao diện dòng lệnh sẽ xuất hiện trong cửa sổ **Run** của IDE.
4.  Sử dụng thông tin đăng nhập từ các tệp `members.csv` hoặc `librarians.csv` để bắt đầu sử dụng hệ thống.
