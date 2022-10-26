package com.unascribed.fabrication.compat;

import java.nio.file.Path;

import com.unascribed.fabrication.EarlyAgnos;

public interface FabricLoader {

	FabricLoader INST = new FabricLoader() {
		@Override
		public Path getConfigDir() {
			return EarlyAgnos.getConfigDir();
		}
	};

	static FabricLoader getInstance() {
		return INST;
	}

	Path getConfigDir();

}
