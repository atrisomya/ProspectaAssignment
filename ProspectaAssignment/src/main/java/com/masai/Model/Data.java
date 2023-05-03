package com.masai.Model;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;

@lombok.Data
@AllArgsConstructor
public class Data {
	private Integer count;
	private List<Entry> entries;
	public Data() {
		this.entries = new ArrayList<>();
	}
}
