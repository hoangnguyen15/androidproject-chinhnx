package com.krazevina.thioto;

import com.krazevina.thioto.R;

import android.app.Activity;
import android.os.Bundle;
//import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TabWidget;
import android.widget.TextView;

public class Meothi extends Activity {

	TextView content;
	TextView content1;
	TextView content2;
	TextView content3;
	TextView content4;
	Button back;

	@Override
	public void onCreate(Bundle save) {
		super.onCreate(save);
		setContentView(R.layout.meothi);
		content = (TextView) findViewById(R.id.content);
		content1 = (TextView) findViewById(R.id.content1);
		content2 = (TextView) findViewById(R.id.content2);
		content3 = (TextView) findViewById(R.id.content3);
		content4 = (TextView) findViewById(R.id.content4);
		back = (Button) findViewById(R.id.back);
//		Log.d("length",""+noidung.length());
		content.setText(noidung.substring(0, 4000));
		content1.setText(noidung.substring(4000,8000));
		content2.setText(noidung.substring(8000,12000));
		content3.setText(noidung.substring(12000));
		
		back.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Meothi.this.finish();
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
	
	String noidung = "\t1 . Có từ \"đường bộ\": đ/án 2"
			+ "\n\t2 . Đề có từ 3>4 đ/án: chọn đ/án \"cả\" hoặc \"tất cả\". Trừ câu 3 \"phần đường xe chạy\" và câu 146 \"cung cấp nhiên liệu cho động cơ xăng\" chọn đ/án 1."
			+ "\n\t3 . Nồng độ cồn trong: máu 80 (đ/án 2); khí thở 40 (đ/án 1)"
			+ "\n\t4 . Tuổi lái xe: đ/án 2."
			+ "\n\t5 . Đường cao tốc câu có 2 đáp án chọn đ/án 1."
			+ "\n\t6 .Quy định các phương tiện tham gia giao thông:"
			+ "\n-Câu có từ\"nguy hiểm\"; \"đặc biệt\" chọn đ/án có từ \"chính phủ\"."
			+ "\n-Câu có từ\"địa phương quản lý\" chọn đ/án có từ \"UBND Tỉnh\"."
			+ "\n-Các câu còn lại chọn đ/án \"Bộ giao thông\";\"cơ quan quản lý GT\"."
			+ "\t7 .Các đáp án có từ : \"Tuyệt đối ko\";\"Tuyệt đối cấm\";\"Cấm\": Chọn"
			+ "\t8 .Kéo xe mất hãm: \"thanh nối cứng\"."
			+ "\t9 .Cấm bóp còi từ\" 22h>5h sáng hôm sau\"; còi vang xa 100m đồng giọng; 65>115dB."
			+ "\t10 .Kinh doanh vận tải xe buýt: chọn đáp án dài hơn."
			+ "\t11 .Mục đích điều khiển trong hình số 3, 8: chọn đ/án1."
			+ "\t12 .Thể tích buồng cháy (Vc: đ/án1): Nắp máy>ĐCTrên"
			+ "\n-Buồng công tác(Vh: đ/án2):Nắp máy > ĐC Dưới."
			+ "\n-Buồng làm việc(Vs: đ/án3): ĐC Trên> ĐC Dưới"
			+ "\n\t13 .Độ rơ tay lái:-con (100:đ/án 1)"
			+ "\n-khách>12 chỗ (200:đ/án 2)"
			+ "\n-tải>1,5T (250:đ/án 3)."
			+ "\n14 .Yêu cầu của hệ thống lái: đ/án 1."
			+ "\n\t15 .Công dụng hộp số: đ/án 1."
			+ "\n\t16 .Điều chỉnh đánh lửa \"sớm sang muộn\" chọn \"cùng chiều\" đ/án 1)"
			+ "\"muộn sang sớm\" chọn \"ngược chiều\" đ/án 2)."
			+ "\n\t17 .Gương chiếu hậu: nhìn sau 20m"
			+ "\n\t18 .Bảng hiệu hướng đi phải theo 301i: chọn đ/án 3; trừ câu 206: \"biển nào không cho phép rẽ phải\" chọn đ/án 1."
			+ "\n\t19 .Câu sa hình có 4 xe: chọn đ/án 3; Trừ câu 300 đ/án 1."
			+ "\n\t20 .Có cảnh sát giao thông đứng: chọn đ/án 3."
			+ "\n\t21 .Quy tắt giải sa hình: nhất chớm-nhì ưu-tam đường-tứ hướng"
			+ "\n-ưu: chữa cháy>công an; quân sự> cứu thương> Hộ đê, PC bão lụt> Cảnh sát dẫn đường> xe tang> Do thủ tướng CP quy định"
			+ "\n-hướng:-ngã 3,4: bên phải không vướng"
			+ "\n-vòng xuyến: nhường đường xe bên trong"
			+ "\n\t22 .Cứ gặp câu hỏi cách đường ray bao nhiêu, thì là 5 mét"
			+ "\n\t23 . Cắt ngang đoàn xe, đoàn người đi lại có tổ chức, bao gồm cả đoàn xe tang: cấm chỉ, do vậy cứ hễ gặp đoàn người đoàn xe là ôtômatích không có cắt ngang qua"
			+ "\n\t24 .Chú ý, đề thi hay bẫy ở những chỗ hết sức \"vớ vẩn\", như kiểu, ý 1: Biển 2, ý 2: Biển 3, ý 3: Biển 1, nhưng hấp tấp không chú ý là mất điểm"
			+ "\n\t25. Biển cho phép quay đầu 409 và 410, chỉ cho quay đầu mà cấm rẽ trái, chú ý ở các câu 188, 189."
			+ "\nCâu hỏi sa hình: cứ 4 xe là chọn ý 3, trừ câu có chữ CA bên hông chọn ý 1"
			+ "\n- Câu hỏi biển báo: câu hỏi biển tròn xanh, dài 1 dòng--->ý1, còn lại ý 3"
			+ "\n- Cấm tải, kéo: ý cuối"
			+ "Để trả lời các câu hỏi về sa hình, bạn nhớ các nguyên tắc về ưu tiên sau:"
			+ "\n\t1 . Xe nào đã vào ngã tư thì xe đó có quyền ưu tiên đi trước cao nhất."
			+ "\n\t2 . Tiếp đó đến các xe ưu tiên. Trong các xe ưu tiên thì xe cứu hoả có ưu tiên xe quân sự, xe công an, xe cứu thương."
			+ "\n\t3 . Tiếp đó nếu cùng là xe ưu tiên hoặc cùng là xe không ưu tiên thì xét đến đường ưu tiên, tức là xe nào nằm trên đường ưu tiên thì có quyền đi trước."
			+ "\n\t4 . Xe nào không vướng xe khác ở bên phải có quyền đi trước, nhưng trong vòng xuyến thì phải nhường đường cho xe đến từ bên trái."
			+ "\n\t5 . Thứ tự ưu tiên tiếp theo: xe rẽ phải, xe đi thẳng, xe rẽ trái"
			+ "Hướng dẫn chi tiết từng bài thi Sa Hình"
			+ "\n\tBài 1. Xuất phát"
			+ "\nYêu cầu của bài này là khi xuất phát phải bật đèn xi-nhan trái (với ý nghĩa là xe chuẩn bị đi ra làn đường bên ngoài, hoà vào dòng xe trên đường). Có chỗ yêu cầu phải tắt xi-nhan đúng lúc, để xi-nhan bật lâu quá trừ 5 điểm. Có chỗ yêu cầu trước khi xuất phát về đưa số về 0, khi cho lệnh xuất phát mới vào số 1 để đi."
			+ "\nTrước lúc xuất phát, khi mới lên xe, bạn cần kiểm tra lại ghế ngồi xem có phù hợp với người không, nếu cần thiết thì chỉnh xa vành tay lái hoặc gần lại để đạp hết được côn, phanh, ga. Kiểm tra hai gương sao cho nhìn thấy được điểm bánh xe sau tiếp xúc với mặt đường."
			+ "\nKhi có lệnh xuất phát, bạn vào số 1, nhả côn từ từ để xe đi. Khi đèn xanh trong xe tắt hoặc khi qua vạch xuất phát rồi thì tắt xi-nhan. Khi xe đã đi, bạn có thể nhả hết côn ra cho xe tự bò, không cần đặt chân vào bàn đạp ga. Nhưng theo tôi, bạn không nên nhả hết mà cứ đỡ côn ở mức một nửa để xe đi chậm, chuẩn bị vào bài 2."
			+ "\n\tBài 2. Dừng xe nhường đường cho người đi bộ"
			+ "\nYêu cầu của bài này là dừng xe đúng chỗ trước vạch trắng và đường vằn dành cho người đi bộ. Đỗ già quá (chạm vào vạch trắng) hoặc non quá (quá xa vạch trắng) đều bị trừ 5 điểm. Các sân thi thường \"giúp\" học viên bằng cách đánh dấu sẵn bằng vạch đỏ trên vỉa ba-toa hoặc ngay trên mặt đường. Vạch đỏ trên vỉa ba-toa để chỉ khi vai người lái xe đến ngang vạch đó thì phải dừng. Còn với vạch đỏ trên mặt đường thì phải nhìn qua gương thấy bánh xe sau cách vạch đỏ chừng hơn gang tay là dừng. Hoặc người lái cũng có thể lấy cột biển báo hiệu người đi bộ trồng bên phải đường để làm cột mốc dừng cho mình."
			+ "\nSau khi xuất phát, bạn để xe đi chậm. Càng vào đến bài thi càng chậm, để khi bạn thấy đúng vị trí thì chỉ cần ấn nhẹ phanh là xe đã dừng ngay và dừng nhẹ nhàng (không giật nẩy lên)."
			+ "\nDừng xe xong, bạn lại nhả côn cho xe đi tiếp luôn. Dừng lâu quá 30 giây sẽ bị trừ điểm."
			+ "\n\t3. Dừng xe, khởi hành trên dốc lên"
			+ "\nYêu cầu của bài này là xe không vượt quá vạch quy định (vượt sẽ bị loại ngay!), không bị tuột dốc quá 50 cm, phải vượt khỏi dốc trong khoảng thời gian 30 giây, không được tăng ga quá lớn (số vòng quay động cơ không quá 3 hoặc 4 nghìn vòng/phút). Chính vì nếu vượt quá vạch quy định là bị loại ngay nên nhiều người đành phải đỗ non khi chưa đến đúng vị trí, chấp nhận mất 5 điểm cho chắc ăn."
			+ "\nSau khi qua bài 2, bạn nhả hết côn, phanh cho xe tự bò lên dốc. Về bản chất, bài này giống bài 2 ở chỗ dừng xe rồi lại đi tiếp. Nhưng vì xe đang ở trên dốc nên bạn không thể đỡ côn cho xe đi chậm lại vì nếu đỡ côn thì xe sẽ bị trôi ngược về chân dốc. Vì thế, chỉ có thể nhắm đúng vị trí cần đỗ (qua vạch đỏ trên ta-luy hoặc mặt đường) để đạp côn, phanh đúng lúc."
			+ "\nNếu như ở bài 2, sau khi dừng xe, để đi tiếp bạn chỉ việc bỏ chân phanh ra rồi mới từ từ nhả côn. Nhưng ở bài 3 thì không thể làm như vậy vì xe đang trên dốc, bỏ phanh chân ra thì xe sẽ trôi. Do vậy cách xử lý ở bài 3 khác bài 2. Có hai cách:"
			+ "\n- Cách 1: Là cách dạy chính thống trong trường. Sau khi xe đã dừng trên dốc, bạn kéo phanh tay với mục đích là thay phanh chân giữ xe tại điểm dừng. Khi đó, bạn có thể bỏ chân phanh ra và đặt vào chân ga mớm lên. Đồng thời chân trái nhả côn từ từ, đến khi thấy tay lái hoặc cần số rung lên (báo hiệu các lá côn đã bắt vào nhau) thì nhả nhẹ phanh tay, nghe ngóng nếu thấy xe không trượt thì thả nốt phanh tay, xe sẽ tự bò lên."
			+ "\n- Cách 2: Là cách các lái già thường làm trong thực tế, không dùng đến phanh tay. Sau khi xe dừng, bạn nhả côn từ từ, đến khi thấy tay lái hoặc cần số rung lên thì nhả nhẹ phanh chân, nghe ngóng. Nếu cảm thấy xe trôi thì đạp phanh vào, làm lại. Nếu thấy xe không trượt thì thả cho hết phanh chân, xe sẽ tự bò lên. Nếu nhả hết phanh chân mà xe vẫn đứng yên thì tiếp vào chân ga một chút, đồng thời hơi nhả côn ra thêm. Khi xe đã đi thì giữ nguyên vị trí chân côn và ga cho đến khi xe qua khỏi đỉnh dốc. Nhiều người mới học lại thấy cách làm này dễ hơn cách 1, vì không cần dùng đến phanh tay mà chỉ tập trung vào hai chân điều chỉnh côn, phanh (thực tế khi hạ phanh tay, những người chưa quen có thể bị choạng tay lái hoặc ấn mạnh vào bàn đạp ga làm rú ga)."
			+ "\n\t4. Đi xe qua hàng đinh"
			+ "\nYêu cầu của bài này là hai bánh xe bên phải phải đi lọt qua một đoạn đường có bề rộng khoảng 30-35 cm. Nếu chạm vào mép bên nào cũng là bị trừ 5 điểm."
			+ "\nKhi rẽ vào đường đi hàng đinh, bạn nên đánh lái muộn một chút để xe áp sát vỉa ba-toa bên phải xe. Đi thật chậm và nhìn gương phải để quan sát bánh xe phía sau. Các sân thi thường kẻ sẵn vạch đánh dấu màu đỏ để giúp học viên căn đường. Vạch này bằng với mép ngoài của hàng đinh. Vì vậy, nếu bánh xe cách vạch đỏ khoảng 10-15 cm thì nhiều khả năng xe sẽ đi qua hàng đinh mà không chạm mép hai bên."
			+ "\nNgoài việc nhìn gương phải, bạn cũng phải căn và bám vào một điểm mốc ở phía trước, thường là một vạch đánh dấu trên vỉa ba-toa trước mặt. Vì có khi lúc đầu xe đi đúng khoảng cách với vạch đỏ, nhưng sau đó do giữ lái không tốt nên xe bị chệch ra hoặc chệch vào."
			+ "\n\t5. Đi xe qua đường vuông góc (chữ Z)"
			+ "\nYêu cầu của bài này là khi cho xe đi không bị chạm vạch ở gần vỉa hè hai bên đường, nếu chạm vạch trừ 5 điểm."
			+ "\nSau khi đi qua hàng đinh, bạn thấy người ngang với vỉa ba-toa vuông góc bên trái thì đánh hết lái sang trái. Đi từ từ và trả lái, đến khi người ngang với vỉa ba-toa vuông góc bên phải thì lại đánh hết lái sang phải. Qua khỏi điểm vuông góc thứ hai, nhớ trả lái cho xe thẳng."
			+ "\n\t6. Đi xe qua đường vòng quanh co (chữ S)"
			+ "\nYêu cầu của bài này giống bài 5."
			+ "\nKhác với bài 5, do chữ S là đường cong liên tục nên bạn phải điều chỉnh tay lái theo đường cong. Các lái xe có câu \"Tiến bám lưng, lùi bám bụng\", có nghĩa là khi xe vào đường cua (ôm cua) nên căn theo phía đường cong dài hơn. Như vậy, khi vào đường chữ S, bạn cho xe bám sát về bên phải, đánh lái sang trái cho xe đi nửa vòng cua đầu tiên, sau đó lại bám sang lề đường bên trái, trả lái và đánh lái sang phải cho xe qua nốt nửa vòng cua còn lại."
			+ "\n\t7. Ghép xe vào nơi đỗ (lùi chuồng)"
			+ "\nYêu cầu của bài này là trong vòng 2 phút bạn phải cho xe lùi được vào nơi đỗ (chuồng), không chạm vạch và tiến ra khỏi chuồng. Không được để xe chèn lên vỉa ba-toa, nếu không sẽ bị loại."
			+ "\nKhi bắt đầu rẽ vào khu vực chuồng, bám sát lề đường bên trái. Đi chậm. Khi người đi ngang qua cửa chuồng thì đánh hết lái về bên phải. Khi thấy xe ở khoảng 45 độ so với đường ngang cửa chuồng thì dừng xe, trả lái cho bánh xe thẳng. Vào số lùi."
			+ "\nNhiều người sợ bài này vì không biết lúc nào nên đánh lái sang trái để xe vào đúng cửa chuồng. Do vậy, bạn phải chỉnh gương sao cho nhìn được chỗ bánh sau bên trái xe tiếp xúc với mặt đất. Lùi thẳng xe cho đến khi thấy chỗ bánh sau này cắt ngang đường vạch trắng bên trong chuồng kéo dài ra thì đánh hết lái sang trái, nhiều khả năng xe sẽ vào đúng cửa chuồng."
			+ "\nCòn nếu không, ngay từ khi xe bắt đầu lùi, bạn đã đánh lái sang trái một chút. Khi xe lùi một đoạn, vào gần cửa chuồng hơn thì nhìn qua gương, bạn có thể hình dung vị trí tương đối của xe so với cửa chuồng, từ đó quyết định lùi thẳng tiếp, đánh thêm lái sang trái hoặc sang phải."
			+ "\nKhi xe đã vào đến cửa chuồng và thân xe song song với hai bên chuồng, trả lái sang phải cho bánh xe thẳng. Nếu chưa quen, trước khi trả lái, bạn dừng hẳn xe lại rồi mới xoay tay lái (gọi là đánh lái chết). Lùi từ từ thẳng vào chuồng cho đến khi nghe hiệu lệnh \"Đã kiểm tra\" thì dừng lại. Về số 1 và tiến ra khỏi chuồng."
			+ "\nLưu ý khi tiến ra, người phải ra khỏi cửa chuồng hoặc hơn một chút nữa bạn hãy đánh lái rẽ sang phải để tránh trường hợp bánh sau chưa ra khỏi cửa chuồng mà đã rẽ sẽ bị chèn vạch, trừ điểm."
			+ "\nNếu lỡ lùi chưa chính xác, đuôi xe cách xa cửa chuồng, có thể chèn lên vạch hoặc vỉa ba-toa, bạn cứ bình tĩnh về lại số 1, tiến lên phía trước, đánh lái sao cho xe ở vào vị trí thẳng trước cửa chuồng, sau đó vào số lùi để làm lại việc lùi vào chuồng. Thà bị trừ điểm do chèn vạch hoặc thực hiện bài thi lâu quá 2 phút còn hơn là bị loại do chèn lên vỉa ba-toa!"
			+ "\n\t8. Dừng xe nơi giao nhau với đường sắt"
			+ "Yêu cầu và thực hành của bài này giống bài 2."
			+ "\n\t9. Tăng tốc, tăng số"
			+ "\nYêu cầu của bài này là phải lên được số 2 và đạt tốc độ trên 20 km/h trước biển báo 20 màu xanh (biển báo tốc độ tối thiểu phải đạt 20 km/h), sau đó lại phải về số 1 và giảm tốc độ xuống dưới 20 km/h trước biển báo 20 màu trắng (biển báo tốc độ tối đa không quá 20 km/h)."
			+ "\nSau khi qua nơi giao nhau với đường sắt, bạn rẽ sang đường chuẩn bị tăng tốc. Chỉnh lái cho xe thẳng, giữ chắc tay lái, nhả hết côn, phanh. Nhấn ga để xe tăng tốc. Qua biển \"Tăng số, tăng tốc\", bạn đạp côn, vào số 2. Xong nhả côn ra, lại nhấn ga tiếp. Qua biển 20 màu xanh, đạp cả côn và phanh cho xe đi chậm lại, thậm chí dừng hẳn, về số 1. Nhả phanh, rồi nhả côn từ từ để xe đi qua biển 20 màu trắng."
			+ "\nChú ý là bạn không thể cắt côn để xe trôi từ từ qua biển 20 màu trắng, vì yêu cầu ở đây là bạn phải đi qua biển này khi xe có gài số. Vì thế nếu bạn cắt côn làm bánh răng số không quay thì sẽ bị trừ 5 điểm."
			+ "\n\t10. Kết thúc"
			+ "\nYêu cầu của bài này là đi thẳng qua vạch kết thúc, trước đó phải bật đèn xi-nhan phải (với ý nghĩa là xe tấp vào lề đường bên phải, chuẩn bị dừng hoặc đỗ xe)."
			+ "\nSau khi vòng qua ngã tư lần cuối cùng, bạn chỉnh xe cho thẳng và để xe đi từ từ về vạch xuất phát. Bật xi-nhan bên phải. Chú ý sau khi đã bật xi-nhan thì giữ thẳng tay lái, không đánh lái sang trái sẽ làm tắt đèn xi-nhan, mất điểm. Để cho chắc ăn, bạn có thể dùng ngón giữa tay trái giữ cần xi-nhan để không cho cần này bật xuống, hoặc hơi đánh lái sang phải một chút."
			+ "\nNgoài 10 bài thi trên, còn có 2 bài thi phụ. Gọi là phụ, nhưng bạn cũng có thể mất điểm ở những bài này không khác bài chính."
			+ "\n\t11. Dừng xe nguy hiểm"
			+ "\nKhi xe đi qua một số vị trí trên tuyến đường thi, loa trong xe có thể vang lên \"Dừng xe nguy hiểm! Dừng xe nguy hiểm!\". Khi nghe hiệu lệnh này, bạn nhanh chóng dừng hẳn xe, ấn vào nút đèn báo hiệu nguy hiểm (nút có vẽ hình tam giác). Khi nào loa hết hiệu lệnh trên thì ấn nút lần nữa để tắt đèn và đi tiếp."
			+ "\nTrên sân thi có thể có nhiều vị trí dừng xe nguy hiểm, nhưng mỗi lần thi xe chỉ gặp một lần phải dừng theo kiểu này. Có nghĩa là đã dừng ở chỗ này thì không phải dừng ở chỗ kia nữa."
			+ "\n\t12. Cho xe qua ngã tư có đèn tín hiệu điều khiển giao thông"
			+ "\nTrong toàn bộ bài thi, có 4 lần bạn phải đi qua ngã tư ở giữa sân thi, hai lần đi thẳng, một lần rẽ trái và một lần rẽ phải. Cũng giống như ở ngoài đường, tại ngã tư này có đèn tín hiệu và bạn chỉ được cho xe qua ngã tư khi có đèn xanh. Nếu bạn cho xe qua ngã tư khi đang đèn đỏ sẽ bị trừ 10 điểm. Nếu khi xe vừa đến ngã tư mà có đèn xanh thì bạn có thể qua luôn, còn nếu đèn đỏ thì phải dừng lại trước vạch trắng, nếu vượt quá vạch bị trừ 5 điểm. Khi qua ngã tư có rẽ trái và rẽ phải thì cần bật xi-nhan tương ứng, nếu quên trừ 5 điểm."
			+ "\nCó sân thi ngoài vạch trắng còn có vạch vàng ở phía dưới và yêu cầu xe đỗ phải đúng vị trí giữa vạch trắng và vạch vàng, nếu không cũng trừ điểm. Yêu cầu này tương đối khó, vì thế ở những sân này học viên thường \"trốn\" bằng cách đỗ dưới vạch vàng, tức là xa ngã tư hơn, nhưng như vậy khi có đèn xanh thì phải nhanh chóng khởi hành đi tiếp, nếu không rất có thể đèn đã chuyển sang đỏ khi bạn gần vào đến ngã tư - trừ điểm."
			+ "\nMột số điểm cần lưu ý :"
			+ "\nThuộc hình và thuộc cách chạy trong từng hình."
			+ "\n\t1. Kỹ thuật tốt (phối hợp nhịp nhàng chân côn và ga)."
			+ "\n\t2. Tâm lý ổn định không được run."
			+ "\n\t3. Luôn luôn chạy ở số 1 và chạy chậm 10km/h, bác nào mà cài số 2 chạy 1 hồi quên mất là sẽ gặp rất nhiều sự cố."
			+ "\n\t4. Khi chạy tập trung vào từng hình ví dụ đến bài \"dừng khởi hành xe ngang dốc\" thì quên các hình kia đi tập trung và mỗi hình này thội"
			+ "\n\t5. Không bao giờ được đi gần thằng đi trước mình (hạn chế tầm nhìn, không căn được đường, khi vào hình sẽ bị tính thời gian, nếu đi sát quá thì thằng trước mà đè vạch thì mình cũng bị dính theo)"
			+ "\n\t6. Khi ở vạch xuất phát có quá nhiều xe chờ đi thì rất dễ xảy ra trường hợp loạn chip, xe của mình đang ở chỗ vòng cua để vào vạch xuất phát thì đã báo là bài thi bắt đầu rùi, lúc lên được vạch xuất phát thì đã bị chậm ---->trượt luôn)"
			+ "\n\t7. Đoạn tình huống dừng xe khẩn cấp, phải chủ động đi chậm, để sẵn chân sang phanh, đi bằng côn thui, có còi báo động thì dừng lại ấn phím nhan đi thẳng, khi nghe tiếng Boonggggg thì mới tắt nhan và bắt đầu đi."
			+ "\nMột số hình dễ rớt :"
			+ "\n\t1.\"Dừng khởi hành xe ngang dốc\" - với hình này tâm lý bác nào cứng ăn trọn số điểm, sợ thì mất 5 điểm, khi chạy hình này áp dụng kỹ thuật vê côn (buông côn 3/4 , đi ga to hơn) làm cho xe bò chậm lên dốc có như vậy mới canh vạch bánh sau được, bác nào nhấn ga chạy cái ào lên là tiêu."
			+ "\n\t2. \"Ghép xe vào nơi đỗ\" - hình này các bác nào de chưa quen khi nhìn gương hậu thấy có khả năng cán vạch thì cứ bình tĩnh cài số 1 tiến lên xào 1 nhát vào tốt. khi vào hình này các bác chú ý là các xe số móc (lanos...) để cài số de cho đúng. nếu bác nào đi thi de mà lỡ cán vạch thì lập tức nhanh chóng cài số 1 chạy ra sau đó mới de lại tuyệt đối không được đánh lái làm thế nó sẽ trừ tiếp điểm."
			+ "\n\t3. \"qua vệt bánh xe\" - hình này tiêu chí thà cán vạch chứ không chạy lọt ra ngoài mà thật sự hình này khó mà lọt ra ngoài thế mà vẫn có bác chạy lọt.";

}
