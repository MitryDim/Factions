package com.massivecraft.factions.event;

import org.bukkit.command.CommandSender;
import org.bukkit.event.HandlerList;

import com.massivecraft.factions.entity.BoardColl;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.massivecore.ps.PS;

public class EventFactionsChunkChange extends EventFactionsAbstractSender
{	
	// -------------------------------------------- //
	// REQUIRED EVENT CODE
	// -------------------------------------------- //
	
	private static final HandlerList handlers = new HandlerList();
	@Override public HandlerList getHandlers() { return handlers; }
	public static HandlerList getHandlerList() { return handlers; }
	
	// -------------------------------------------- //
	// FIELDS
	// -------------------------------------------- //
	
	private final PS chunk;
	public PS getChunk() { return this.chunk; }
	
	private final Faction currentFaction;
	private final Faction newFaction;
	public Faction getNewFaction() { return this.newFaction; }
	
	// -------------------------------------------- //
	// CONSTRUCT
	// -------------------------------------------- //
	
	public EventFactionsChunkChange(CommandSender sender, PS chunk, Faction newFaction)
	{
		super(sender);
		this.chunk = chunk.getChunk(true);
		this.currentFaction = BoardColl.get().getFactionAt(chunk);
		this.newFaction = newFaction;
	}
	
	// -------------------------------------------- //
	// UTIL
	// -------------------------------------------- //
	
	public EventFactionsChunkChangeType getType()
	{
		if (currentFaction.isNone()) return EventFactionsChunkChangeType.BUY;
		if (newFaction.isNormal()) return EventFactionsChunkChangeType.CONQUER;
		
		MPlayer usender = this.getMSender();
		if (usender != null && usender.getFaction() == currentFaction) return EventFactionsChunkChangeType.SELL;
		
		return EventFactionsChunkChangeType.PILLAGE;
	}
	
}
