package io.github.qMartinz.paranormal.api;

import io.github.qMartinz.paranormal.api.powers.AbstractPower;
import io.github.qMartinz.paranormal.api.powers.PowerRegistry;
import io.github.qMartinz.paranormal.api.rituals.AbstractRitual;
import io.github.qMartinz.paranormal.api.rituals.RitualRegistry;
import io.github.qMartinz.paranormal.networking.ModMessages;
import io.github.qMartinz.paranormal.server.data.StateSaverAndLoader;
import io.github.qMartinz.paranormal.util.NBTUtil;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.quiltmc.qsl.networking.api.PacketByteBufs;
import org.quiltmc.qsl.networking.api.ServerPlayNetworking;
import org.quiltmc.qsl.networking.api.client.ClientPlayNetworking;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class PlayerData {
	public int pex = 0;
	public int xp = 0;
	public int attPoints = 0;

	/*
	0 = str
	1 = vig
	2 = pre
	 */
	public int[] attributes = new int[]{0, 0, 0};

	public int ritualSlots = 0;
	public int powerPoints = 0;

	public int maxOccultPoints = 4;
	public int occultPoints = 4;

	// Rituals
	public Set<AbstractRitual> rituals = new HashSet<>();
	// Powers
	public Set<AbstractPower> powers = new HashSet<>();

	public void setPex(int amount){
		pex = Math.max(0, Math.min(amount, 20));
	}

	public int getPex(){
		return pex;
	}

	public void setXp(int amount) {
		xp = amount;
	}

	public void addXp(int amount){
		if (amount > 0) {
			int totalXp = xp + amount;
			while (totalXp >= (pex + 1) * 50 && pex < 20) {
				totalXp -= (pex + 1) * 50;
				pex++;
				// +1 attPoint each 10%
				if ((pex - 1) % 2 == 0) attPoints++;

				// +2 ritualSlots in 5% and +1 ritualSlot each 20%
				if (pex % 4 == 0) ritualSlots++;
				if (pex == 1) ritualSlots += 2;

				// +1 powerPoint each 15% and +1 powerPoint in 99%
				if ((pex - 1) % 3 == 0) powerPoints++;
				if (pex == 20) powerPoints++;
			}

			xp = totalXp;
		}
	}

	public int getXp(){
		return xp;
	}

	public int getNexPercentage(){
		if (pex == 20) return 99;
		return pex*5;
	}

	public void setAttPoints(int attPoints) {
		this.attPoints = attPoints;
	}

	public int getAttPoints() {
		return attPoints;
	}

	public void setAttribute(int index, int value) {
		this.attributes[index] = value;
	}

	public int getAttribute(int index){
		return attributes[index];
	}

	public void setPowerPoints(int powerPoints) {
		this.powerPoints = powerPoints;
	}

	public int getPowerPoints() {
		return powerPoints;
	}

	public void setRitualSlots(int ritualSlots) {
		this.ritualSlots = ritualSlots;
	}

	public int getRitualSlots() {
		return ritualSlots;
	}

	public void setMaxOccultPoints(int maxOccultPoints) {
		this.maxOccultPoints = Math.max(1, maxOccultPoints);
	}

	public int getMaxOccultPoints() {
		return maxOccultPoints;
	}

	public void setOccultPoints(int occultPoints) {
		this.occultPoints = Math.max(0, Math.min(occultPoints, getMaxOccultPoints()));
	}

	public int getOccultPoints() {
		return occultPoints;
	}

	public void clearRituals(){
		this.rituals.clear();
	}

	public void addRitual(AbstractRitual ritual){
		this.rituals.add(ritual);
	}

	public void removeRitual(AbstractRitual ritual){
		this.rituals.remove(ritual);
	}

	public AbstractRitual getRitual(int index){
		return rituals.stream().toList().get(index);
	}

	public boolean hasRituals(AbstractRitual ritual){
		return this.powers.contains(ritual);
	}

	public void clearPowers(){
		this.powers.clear();
	}

	public void addPower(AbstractPower power){
		this.powers.add(power);
	}

	public void removePower(AbstractPower power){
		this.powers.remove(power);
	}

	public AbstractPower getPower(int index){
		return powers.stream().toList().get(index);
	}

	public boolean hasPower(AbstractPower power){
		return this.powers.contains(power);
	}

	public NbtCompound serializeRituals() {
		NbtCompound nbt = new NbtCompound();

		NBTUtil.writeStrings(nbt, "ritual", rituals.stream().map(AbstractRitual::getId).map(Identifier::toString).collect(Collectors.toList()));

		return nbt;
	}

	public NbtCompound serializePowers() {
		NbtCompound nbt = new NbtCompound();

		NBTUtil.writeStrings(nbt, "power", powers.stream().map(AbstractPower::getId).map(Identifier::toString).collect(Collectors.toList()));

		return nbt;
	}

	public void deserializeRituals(NbtCompound nbt) {
		clearRituals();
		for(String s : NBTUtil.readStrings(nbt, "ritual")){
			if(RitualRegistry.rituals.containsKey(Identifier.tryParse(s))){
				rituals.add(RitualRegistry.getRitual(Identifier.tryParse(s)).get());
			}
		}
	}

	public void deserializePowers(NbtCompound nbt) {
		clearPowers();
		for(String s : NBTUtil.readStrings(nbt, "power")){
			if(PowerRegistry.powers.containsKey(Identifier.tryParse(s))){
				powers.add(PowerRegistry.getPower(Identifier.tryParse(s)).get());
			}
		}
	}

	public void syncToClient(ServerPlayerEntity player){
		PlayerData playerState = StateSaverAndLoader.getPlayerState(player);
		PacketByteBuf data = PacketByteBufs.create();
		data.writeInt(playerState.pex);
		data.writeInt(playerState.xp);
		data.writeInt(playerState.attPoints);
		data.writeIntArray(playerState.attributes);
		data.writeInt(playerState.ritualSlots);
		data.writeInt(playerState.powerPoints);
		data.writeInt(playerState.maxOccultPoints);
		data.writeInt(playerState.occultPoints);

		data.writeNbt(playerState.serializeRituals());
		data.writeNbt(playerState.serializePowers());

		ServerPlayNetworking.send(player, ModMessages.PLAYER_DATA_SYNC_ID, data);
	}
	public void syncToServer(){
		PacketByteBuf data = PacketByteBufs.create();
		data.writeInt(pex);
		data.writeInt(xp);
		data.writeInt(attPoints);
		data.writeIntArray(attributes);
		data.writeInt(ritualSlots);
		data.writeInt(powerPoints);
		data.writeInt(maxOccultPoints);
		data.writeInt(occultPoints);

		data.writeNbt(serializeRituals());
		data.writeNbt(serializePowers());

		ClientPlayNetworking.send(ModMessages.PLAYER_DATA_SYNC_ID, data);
	}
}
