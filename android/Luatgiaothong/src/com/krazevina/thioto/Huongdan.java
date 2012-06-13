package com.krazevina.thioto;

import com.krazevina.thioto.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TabWidget;
import android.widget.TextView;

public class Huongdan extends Activity {

	TextView content;
	Button back;

	@Override
	public void onCreate(Bundle save) {
		super.onCreate(save);
		setContentView(R.layout.huongdan);
		content = (TextView) findViewById(R.id.content);
		back = (Button) findViewById(R.id.back);
		content.setText(noidung);
		back.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Tab.tabwidget.setVisibility(TabWidget.VISIBLE);
				Tab.tabHost.setCurrentTab(Tab.currentTab);
				

			}
		});
	}
	
	@Override
	protected void onResume() {
	//	Tab.tabHost.setVisibility(TabHost.VISIBLE);
		super.onResume();
	}

	@Override
	public void onBackPressed() {

		Tab.tabwidget.setVisibility(TabWidget.VISIBLE);
		Tab.tabHost.setCurrentTab(Tab.currentTab);
		
	}
	
	String noidung = "\t1. Bộ luật:"
		        	+ "\n - Gồm 8 chương, để xem thông tin từng chương bạn chọn vào chương tương ứng,"
			+ "mỗi chương được chia nhỏ thành các điều, để chuyển sang điều kế tiếp bạn có thể vuốt sang trái , sang phải hoặc có thể sử dụng nút chọn."
			+ "\n\t2. Biển báo:"
			+ "\n - Biển báo chia thành 6 loại gồm có: "
			+ "\n    Biển cấm, biển nguy hiểm ,biển hiệu lệnh ,biển phụ , và vạch kẻ. "
			+ "\n - Bạn muốn xem chi tiết biển nào thì chọn biển đó. "
			+ "\n - Ngoài ra còn có chức năng tìm kiếm biển, bạn nhập cụm từ và tìm kiếm. "
			+ "\n\t3. Bài thi:"
			+ "\n - Bài thi gồm có 30 câu, thời gian làm bài là 20 phút, nhấp \"Bắt đầu thi\"để bắt đầu làm bài "
			+ "\n - Trong quá trình làm bài bạn có thể xem nhưng câu đã làm và câu chưa làm bằng cách nhấn nút \ndanh sách\n nếu chuyển sang tab khác có thể bài thi sẽ kết thúc. "
			+ "\n\t4. Bộ đề:"
			+ "\n - phần chia thành 3 nhóm gồm có: "
			+ "\n  Câu hỏi luật , câu hỏi biển báo , câu hỏi tình huống. "
			+ "\n - Bạn sẽ chọn các đáp án mình cho là đúng và có thể nhấp \"Xem trả lời\" để biết mình chọn đúng hay sai, màu xanh<=> đúng, màu đỏ <=> sai, màu vàng <=> bạn đã chọn. "
			+ "\n\t5. Kết quả:"
			+ "\n -phần kết quả thi: Nếu bạn vừa kết thúc bài thi, nó sẽ hiện thị lại các đáp án bạn đã làm , đồng thời tính toán số câu đúng sai, và quan trọng nhất là bạn \"đỗ\" hay \"trượt\" trong bài thi vừa rồi. "
			+ "\n -phần thống kê :thống kê lại những lần bạn đã thi, số câu đúng sai thời gian bắt đầu, kết thúc bài thi, và tính điểm trung bình."
			+ "\n NẾU THẤY HAY BẠN HÃY ĐÓNG GÓP CHO CHỦ NHÂN PHẦN MỀM, BẮNG CÁCH: GỬI TIN SMS NHÉ!";


}
