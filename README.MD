# Đồ án phát triển ứng dụng cảnh báo kẹt xe sử dụng RESTful API thông qua ASP.NET CORE

Đây là dự án phát triển ứng dụng cảnh báo kẹt xe dựa trên thông báo của người dùng tương tác trực tiếp với database thông qua ASP.NET CORE API

## Hướng dẫn cài đặt

Đây là hướng dẫn cài đặt bao gồm cách chạy dự án API sửa lại thông tin của app android để chạy và kết nối database

### CẦN PHẢI CÓ

Trên máy đã cài đặt chương trình [Visual Studio](https://visualstudio.microsoft.com/vs/) từ bản 2015 trở lên.

Sau khi chạy file cài đặt hãy tích vào những ô sau để cài được môi trường

![ảnh](https://github.com/Khang0202/DoAn/blob/main/HinhAnhDoAnNganh/cai%20Visual.png)
 
Trên máy đã cài đặt [SQL SERVER 2022](https://go.microsoft.com/fwlink/p/?linkid=2215158&clcid=0x409&culture=en-us&country=us)

Trên máy đã cài đặt phần mềm quản lý SSMS [SQL Server Management Studio](https://learn.microsoft.com/en-us/sql/ssms/download-sql-server-management-studio-ssms?view=sql-server-ver16)

![ảnh](https://github.com/Khang0202/DoAn/blob/main/HinhAnhDoAnNganh/TảiSSMS.png)

### Thêm dữ liệu vào database

#Bước 1:Kết nối tới server

![ảnh](https://github.com/Khang0202/DoAn/blob/main/HinhAnhDoAnNganh/Ketnoi1.png)

#Bước 2:Sau khi kết nối với một Server trên máy, click chuột phải vào Databases chọn Restore Database…

![ảnh](https://github.com/Khang0202/DoAn/blob/main/HinhAnhDoAnNganh/Ketnoi2.png)

#Bước 3:Tại giao diện Restore Database chọn Device, click vào nút 3 chấm để hiển thị giao diện thêm file backup

![ảnh](https://github.com/Khang0202/DoAn/blob/main/HinhAnhDoAnNganh/Ketnoi3.png)

#Bước 4:Chọn đến nơi đã clone sẽ hiển thị file backup database

![ảnh](https://github.com/Khang0202/DoAn/blob/main/HinhAnhDoAnNganh/Ketnoi4_1.png)

#Bước 5:Click Ok để phục hồi database

![ảnh](https://github.com/Khang0202/DoAn/blob/main/HinhAnhDoAnNganh/Ketnoi3_1.png)

Chờ sau khi phục hồi thành công sẽ nhận được thông báo

![ảnh](https://github.com/Khang0202/DoAn/blob/main/HinhAnhDoAnNganh/Ketnoi5_1.png)

### Cài đặt

Đây là hướng dẫn từng bước chạy để chạy API


#Bước 1: Tải dự án về

    git clone https://github.com/Khang0202/DoAn
    
Hoặc

![ảnh](https://github.com/Khang0202/DoAn/blob/main/HinhAnhDoAnNganh/taizip.png)

• Nhớ giải nén ra nếu tải bằng zip

#Bước 2: Mở file theo đường dẫn sau: ApiDoAn/ApiDoAn.sln

Sau khi mở lên ta được giao diện như sau

![ảnh](https://github.com/Khang0202/DoAn/blob/main/HinhAnhDoAnNganh/AnhDef.png)

#Bước 3: Chỉnh ip để chạy dự án

  Mở CMD trong máy để lấy ipv4 của máy

![ảnh](https://github.com/Khang0202/DoAn/blob/main/HinhAnhDoAnNganh/cmd.png)

  Đây là ảnh sau khi có được ipv4 của máy

![ảnh](https://github.com/Khang0202/DoAn/blob/main/HinhAnhDoAnNganh/cmdip.png)

   Sau khi lấy được ipv4 thì mở launchSettings.json và sửa phẩn "applicationUrl": "http://[ip của máy]:[port]" trong đó phần port có thể tự đặt theo ý muốn

![ảnh](https://github.com/Khang0202/DoAn/blob/main/HinhAnhDoAnNganh/CaiDatURL.png)

#Bước 4: Chỉnh lại chuỗi kết nối và xác thực

Xác thực

Mở appsettings.json và chỉnh lại phần bôi đen như ảnh như đã chỉnh ở launchSettings.json ở trên "Issuer": "http://[ip của máy]:[port]"

Chuỗi kết nối

Bước 1: nhấn vào View-> SQL Server Object Explorer

![ảnh](https://github.com/Khang0202/DoAn/blob/main/HinhAnhDoAnNganh/Chuoiketnoi1.png)

Bước 2: Nhấn vào add server

![ảnh](https://github.com/Khang0202/DoAn/blob/main/HinhAnhDoAnNganh/Chuoiketnoi2.png)

Bước 3: Sau khi nhấn sẽ hiện ra một bảng như ảnh dưới trong đó (Server Name:) là tên server đã cài ở trong máy có thể nhấn Local để hiện ra danh sách server. (Database Name:) là tên database cần kết nối có thể nhấn mũi tên ở ngoài để hiển thị ra danh sách các database cần thêm vào
           
![ảnh](https://github.com/Khang0202/DoAn/blob/main/HinhAnhDoAnNganh/Chuoiketnoi3.png)

Bước 4: Sau khi cài đặt xong thì nhấn vào phần Advance như hình

![ảnh](https://github.com/Khang0202/DoAn/blob/main/HinhAnhDoAnNganh/Chuoiketnoi4.png)

Sẽ hiển thị lên như hình dưới đây phần bôi đen chính là chuỗi kết nối mà mình cần

![ảnh](https://github.com/Khang0202/DoAn/blob/main/HinhAnhDoAnNganh/Chuoiketnoi5.png)

Bước 5: Sửa lại phần bôi đen của ảnh dưới đây thành chuỗi kết nối ta lấy được ở bước 4

![ảnh](https://github.com/Khang0202/DoAn/blob/main/HinhAnhDoAnNganh/Chuoiketnoi6.png)

#Bước 5: Chạy dự án
 Nhấn F5 để chạy dự án sau khi chạy sẽ hiển thị console và giao diện của swagger (Nếu chạy lần đầu sẽ hiện thông báo cho phép kết nối của tường lửa thì nhấn Allow access)

![ảnh](https://github.com/Khang0202/DoAn/blob/main/HinhAnhDoAnNganh/console.png)

![ảnh](https://github.com/Khang0202/DoAn/blob/main/HinhAnhDoAnNganh/GiaoDienSwagger.png)





## Tác giả

  - **Nguyễn Mạnh Khang** - *Phát triển app* -
    [Khang0202](https://github.com/Khang0202)
 - **Trần Nguyễn An** - *Phát triển RESTful API* -
    [Junkin3](https://github.com/Junkin3)



