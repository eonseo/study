import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.study.live.washer.bean.SWasher;
import com.study.live.washer.bean.Washer;
import com.study.live.washer.bean.WasherUser;
import com.study.live.washer.config.WasherConfig;

public class WasherTest {

	public static void main(String[] args) {
		
		// 스프링의 IoC 컨테이너(빈을 관리하는 통) 생성
		ApplicationContext ctx = new AnnotationConfigApplicationContext(WasherConfig.class);
		
		// 컨테이너 ctx 에서 SWasher 타입의 빈을 꺼내서 washer 변수에 저장
		// 동일 타입이 여러개라면 이름 추가
		Washer washer = ctx.getBean("lWasher", Washer.class);
		
		// 컨테이너 ctx 에서 WasherUser 타입의 빈을 꺼내서 user 변수에 저장
		// 기본적으로는 타입 기반
		WasherUser user = ctx.getBean(WasherUser.class);
		
		user.useWasher(); // washer.wash("바지") = SWasher.wash("바지")

		System.out.println(user.getWasher() == washer);
	}

}
