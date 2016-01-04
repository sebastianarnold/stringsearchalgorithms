package com.almondtools.util.map;


public class HashFunction {

	private final int[] g;
	private final int n;
	private final int seed1;
	private final int seed2;

	public HashFunction(int[] g, int seed1, int seed2) {
		this.g = g;
		this.n = g.length;
		this.seed1 = seed1;
		this.seed2 = seed2;
	}

	public final int hash(long[] key) {
		int[] hashes = doubleHash(key);
		return g[hashes[0]] + g[hashes[1]];
	}

	public int[] doubleHash(long[] key) {
		int h1 = 1;
		for (long k : key)  {
			int k1 = (int) (k & 0x00ff);
			int k2 = (int) (k >>> 32);
			h1 = (h1 * seed1) ^ k1;
			h1 = (h1 * seed1) ^ k2;
		}
		h1 %= n;
		if (h1 < 0) {
			h1 += n;
		}

		int h2 = 1;
		for (long k : key)  {
			int k1 = (int) (k & 0x00ff);
			int k2 = (int) (k >>> 32);
			h2 = (h2 * seed2) ^ k1;
			h2 = (h2 * seed2) ^ k2;
		}
		h2 %= n;
		if (h2 < 0) {
			h2 += n;
		}
		if (h1 == h2) {
			h2 = (h2 + 1) % n;
		}

		return new int[] { h1, h2 };
	}

	public final int hash(int key) {
		int[] hashes = doubleHash(key);
		return g[hashes[0]] + g[hashes[1]];
	}

	public int[] doubleHash(int key) {
		int h1 = (key ^ seed1) % n;
		if (h1 < 0) {
			h1 += n;
		}

		int h2 = (key ^ seed2) % n;
		if (h2 < 0) {
			h2 += n;
		}
		if (h1 == h2) {
			h2 = (h2 + 1) % n;
		}

		return new int[] { h1, h2 };
	}

}
