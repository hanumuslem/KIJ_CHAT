# KIJ_CHAT
Aplikasi Chatting ini digunakan untuk mata kuliah Keamanan Informasi Jaringan (KIJ) 
dilengkapi dengan fitur enkripsi yang menjamin keamanan dalam bertukar pesan  

Pada aplikasi ini terdapat KIJ.sql yang menyimpan database daftar user dan daftar grup. 
Silahkan dapat mengimpor KIJ.sql kedalam database mysql anda.

Format penggunaan chat sebagai berikut :

Login :

C	: LOGIN username password

S	: LOGIN ok/error

Logout:

C	: LOGOUT username

S	: LOGOUT ok/error

Private Message :

C	: PM src_username dest_username message

Create Group :

C	: CG username group_name

S	: CG ok/error

Group Message :

C	: GM src_username group_name message

Broadcast Message :

C	: BM src_username message


