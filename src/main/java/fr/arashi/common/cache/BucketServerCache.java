package fr.arashi.common.cache;

import lombok.RequiredArgsConstructor;
import org.redisson.api.RBucket;

@RequiredArgsConstructor
public class BucketServerCache<T> {

	private final RBucket<T> bucket;

	public T get(){
		return bucket.get();
	}

	public void save(T t){
		bucket.set(t);
	}
}
