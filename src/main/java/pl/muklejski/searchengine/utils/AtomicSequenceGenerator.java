package pl.muklejski.searchengine.utils;


import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Component;

@Component
public class AtomicSequenceGenerator implements SequenceGenerator {

	private final AtomicLong value = new AtomicLong(1);

	@Override
	public long getNext() {
		return value.getAndIncrement();
	}
}
