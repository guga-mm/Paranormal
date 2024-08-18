package io.github.qMartinz.paranormal.api;

import io.github.qMartinz.paranormal.api.powers.ParanormalPower;
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
import org.quiltmc.qsl.networking.api.PacketByteBufs;
import org.quiltmc.qsl.networking.api.ServerPlayNetworking;
import org.quiltmc.qsl.networking.api.client.ClientPlayNetworking;

import java.util.*;
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

	public double maxOccultPoints = 4;
	public double occultPoints = 4;

	// Rituals
	public Set<AbstractRitual> rituals = new HashSet<>();
	// Powers
	public Set<ParanormalPower> powers = new HashSet<>();
	public Set<ParanormalPower> affinities = new HashSet<>();

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

	/**
	 * <p>0 = str
	 * <p>1 = vig
	 * <p>2 = pre
	 */
	public void setAttribute(ParanormalAttribute attribute, int value) {
		this.attributes[attribute.index] = value;
	}

	/**
	 * <p>0 = str
	 * <p>1 = vig
	 * <p>2 = pre
	 */
	public int getAttribute(ParanormalAttribute attribute){
		return attributes[attribute.index];
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

	public void setMaxOccultPoints(double maxOccultPoints) {
		this.maxOccultPoints = Math.max(1, maxOccultPoints);
	}

	public double getMaxOccultPoints() {
		return maxOccultPoints;
	}

	public void setOccultPoints(double occultPoints) {
		this.occultPoints = Math.max(0, Math.min(occultPoints, getMaxOccultPoints()));
	}

	public double getOccultPoints() {
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

	public void clearAffinities(){
		this.affinities.clear();
	}

	public Collection<ParanormalPower> getPowers() {
		return powers;
	}

	public Collection<ParanormalPower> getAffinities() {
		return affinities;
	}

	public void addPower(ParanormalPower power){
		this.powers.add(power);
	}

	public void addAffinity(ParanormalPower power){
		if (hasPower(power)) this.affinities.add(power);
	}

	public void removePower(ParanormalPower power){
		this.powers.remove(power);
	}

	public void removeAffinity(ParanormalPower power){
		this.affinities.remove(power);
	}

	public ParanormalPower getPower(int index){
		return powers.stream().toList().get(index);
	}

	public ParanormalPower getAffinity(int index){
		return affinities.stream().toList().get(index);
	}

	public boolean hasPower(ParanormalPower power){
		return this.powers.contains(power);
	}

	public boolean hasAffinity(ParanormalPower power){
		return this.affinities.contains(power);
	}

	public NbtCompound serializeRituals() {
		NbtCompound nbt = new NbtCompound();

		NBTUtil.writeStrings(nbt, "ritual", rituals.stream().map(AbstractRitual::getId).map(Identifier::toString).collect(Collectors.toList()));

		return nbt;
	}

	public NbtCompound serializePowers() {
		NbtCompound nbt = new NbtCompound();

		NBTUtil.writeStrings(nbt, "power", powers.stream().map(ParanormalPower::getId).map(Identifier::toString).collect(Collectors.toList()));

		return nbt;
	}

	public NbtCompound serializeAffinities() {
		NbtCompound nbt = new NbtCompound();

		NBTUtil.writeStrings(nbt, "affinity", affinities.stream().map(ParanormalPower::getId).map(Identifier::toString).collect(Collectors.toList()));

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

	public void deserializeAffinities(NbtCompound nbt) {
		clearAffinities();

		for(String s : NBTUtil.readStrings(nbt, "affinity")){
			if(PowerRegistry.powers.containsKey(Identifier.tryParse(s))){
				affinities.add(PowerRegistry.getPower(Identifier.tryParse(s)).get());
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
		data.writeDouble(playerState.maxOccultPoints);
		data.writeDouble(playerState.occultPoints);

		data.writeNbt(playerState.serializeRituals());
		data.writeNbt(playerState.serializePowers());
		data.writeNbt(playerState.serializeAffinities());

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
		data.writeDouble(maxOccultPoints);
		data.writeDouble(occultPoints);

		data.writeNbt(serializeRituals());
		data.writeNbt(serializePowers());
		data.writeNbt(serializeAffinities());

		ClientPlayNetworking.send(ModMessages.PLAYER_DATA_SYNC_ID, data);
	}
}
