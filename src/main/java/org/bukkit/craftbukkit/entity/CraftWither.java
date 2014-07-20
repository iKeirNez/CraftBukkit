package org.bukkit.craftbukkit.entity;

import net.minecraft.server.EntityWither;

import org.apache.commons.lang.Validate;
import org.bukkit.Location;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Wither;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.WitherSkull;
import org.bukkit.event.entity.EntityTargetEvent;

public class CraftWither extends CraftMonster implements Wither {
    public CraftWither(CraftServer server, EntityWither entity) {
        super(server, entity);
    }

    @Override
    public EntityWither getHandle() {
        return (EntityWither) entity;
    }

    @Override
    public String toString() {
        return "CraftWither";
    }

    public EntityType getType() {
        return EntityType.WITHER;
    }

    @Override
    public LivingEntity getTarget(WitherHead head) {
        Validate.notNull(head, "Must select a WitherHead to check");
        return getHandle().getHeadTarget(head) != null ? (LivingEntity) getHandle().getHeadTarget(head).getBukkitEntity() : null;
    }

    @Override
    public void setTarget(WitherHead head, LivingEntity entity) {
        Validate.notNull(head, "Must select a WitherHead to set");
        Validate.isTrue(entity == null || getWorld().equals(entity.getWorld()), "Entity must be within the same world as wither");
        getHandle().setHeadTarget(head.getId(), entity != null ? ((CraftLivingEntity) entity).getHandle() : null, EntityTargetEvent.TargetReason.CUSTOM);
    }

    public WitherSkull shoot(WitherHead head, LivingEntity entity) {
        Validate.notNull(head, "Must select a WitherHead to shoot from");
        Validate.notNull(entity, "Must select a valid entity to shoot");
        Validate.isTrue(getWorld().equals(entity.getWorld()), "Entity must be within the same world as wither");
        return (CraftWitherSkull) getHandle().a((head == WitherHead.LEFT ? 2 : head == WitherHead.RIGHT ? 3 : 0), ((CraftLivingEntity) entity).getHandle()).getBukkitEntity();
    }

    public WitherSkull shoot(WitherHead head, Location location) {
        Validate.notNull(head, "Must select a WitherHead to shoot from");
        Validate.notNull(location, "Must select a valid location to shoot at");
        Validate.isTrue(getWorld().equals(location.getWorld()), "Location must be within the same world as wither");
        return (CraftWitherSkull) getHandle().a((head == WitherHead.LEFT ? 2 : head == WitherHead.RIGHT ? 3 : 0) + 1, location.getX(), location.getY(), location.getZ(), false).getBukkitEntity();
    }
}
