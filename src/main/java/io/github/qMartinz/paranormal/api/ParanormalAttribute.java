package io.github.qMartinz.paranormal.api;

import io.github.qMartinz.paranormal.util.CommonText;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

public enum ParanormalAttribute {
	STRENGTH(0, "paranormal.attribute.strength"),
	VIGOR(1, "paranormal.attribute.vigor"),
	PRESENCE(2, "paranormal.attribute.presence");


	public final int index;
	public final String name;

	ParanormalAttribute(int index, String name) {
		this.index = index;
		this.name = name;
	}

	public Text getDisplayName(){
		return Text.translatable(name);
	}

	public List<Text> getDescription() {
		List<Text> lines = new ArrayList<>();
		for (int i = 1; i < 4; i++){
			lines.add(Text.translatable(name + ".description.line_" + i));
			if (i == 1) lines.add(Text.empty());
		}

		return lines;
	}
}
