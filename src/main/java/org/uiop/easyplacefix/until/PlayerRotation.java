package org.uiop.easyplacefix.until;

import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.minecraft.block.enums.DoorHinge;
import net.minecraft.block.enums.Orientation;
import net.minecraft.util.Pair;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

public class PlayerRotation {
    public static Pair<Float, Float> PistonRotation(Direction d) {
        float pitch = switch (d) {
            case DOWN -> -90f;
            case UP -> 90f;
            default -> 0f;
        };
        float yaw = switch (d) {
            case SOUTH -> 180F;
            case WEST -> -90F;
            case EAST -> 90F;
            default -> 0F;
        };
        return new Pair<>(yaw, pitch);
    }

    public static Pair<Float, Float> AnvilRotation(Direction d) {
        float pitch = 0f;
        float yaw = switch (d) {//铁砧的朝向与活塞等的朝向垂直
            // 我感觉你的idea需要先设置一下
            case SOUTH -> -90F;
            case WEST -> 180F;
            case EAST -> -180F;
            default -> 90F;
        };
        return new Pair<>(yaw, pitch);
    }

    public static Pair<Float, Float> SameRotation(Direction d) {//放置的块的水平朝向与玩家朝向匹配

        float pitch = switch (d) {
            case DOWN -> -90f;
            case UP -> 90f;
            default -> 0f;
        };
        float yaw = switch (d) {
            case SOUTH -> 0F;
            case WEST -> 90F;
            case EAST -> -90F;
            default -> 180F;
        };
        return new Pair<>(yaw, pitch);
    }

    public static Pair<Float, Float> SameRotationWithDoor(Direction d, DoorHinge doorHinge, LocalRef<Vec3d> vec3dLocalRef) {//门多了一个hinge属性(左右)

        float yaw = switch (d) {
            case SOUTH -> {
                if (doorHinge == DoorHinge.LEFT)
                    vec3dLocalRef.set(vec3dLocalRef.get().add(0.6f, 0, 0));
                else
                    vec3dLocalRef.set(vec3dLocalRef.get().add(-0.6f, 0, 0)); //right
                yield 0F;
            }
            case WEST -> {
                if (doorHinge == DoorHinge.LEFT)
                    vec3dLocalRef.set(vec3dLocalRef.get().add(0, 0, 0.6f));
                else
                    vec3dLocalRef.set(vec3dLocalRef.get().add(0, 0, -0.6f)); //right
                yield 90F;
            }
            case EAST -> {
                if (doorHinge == DoorHinge.LEFT)
                    vec3dLocalRef.set(vec3dLocalRef.get().add(0, 0, -0.6f));
                else
                    vec3dLocalRef.set(vec3dLocalRef.get().add(0, 0, 0.6f)); //right
                yield -90F;
            }
            default -> {
                if (doorHinge == DoorHinge.LEFT)
                    vec3dLocalRef.set(vec3dLocalRef.get().add(-0.6f, 0, 0));
                else
                    vec3dLocalRef.set(vec3dLocalRef.get().add(0.6f, 0, 0)); //right
                yield 180F;
            }
        };
        return new Pair<>(yaw, 0f);
    }

    public static Vec3d keepHitVec3safe(BlockPos blockPos, Vec3d HitPos) {
        double realHitVec3withX = HitPos.x - (double) blockPos.getX();
        double realHitVec3withY = HitPos.y - (double) blockPos.getY();
        double realHitVec3withZ = HitPos.z - (double) blockPos.getZ();

        if (realHitVec3withX > 1)//确保最终的hitPos合法 而数据包中hitPos的计算方法在net.minecraft.network.PacketByteBuf.writeBlockHitResult
            return new Vec3d((int) HitPos.x, HitPos.y, HitPos.z);
        else if (realHitVec3withX < 0)
            return new Vec3d(blockPos.getX(), HitPos.y, HitPos.z);

        if (realHitVec3withY > 1)
            return new Vec3d(HitPos.x, (int) HitPos.y, HitPos.z);
        else if (realHitVec3withY < 0)
            return new Vec3d(HitPos.x, blockPos.getY(), HitPos.z);

        if (realHitVec3withZ > 1)
            return new Vec3d(HitPos.x, HitPos.y, (int) HitPos.z);
        else if (realHitVec3withZ < 0)
            return new Vec3d(HitPos.x, HitPos.y, blockPos.getZ());

        return HitPos;
    }

    public static Pair<Float, Float> CrafterRotation(Orientation orientation) {

        Direction facing = orientation.getFacing();//决定其是垂直还是水平(水平情况附带朝向)
        Direction rotation = orientation.getRotation();//决定垂直情况下的朝向(水平情况均为UP)
        float yaw;
        float pitch = 0f;
        switch (facing) {
            case UP -> {
                pitch = 90f;
                yaw = switch (rotation) {
                    case SOUTH -> 0F;
                    case WEST -> 90F;
                    case EAST -> -90F;
                    default -> 180F;
                };
            }
            case DOWN -> {
                pitch = -90f;
                yaw = switch (rotation) {
                    case SOUTH -> 180F;
                    case WEST -> -90F;
                    case EAST -> 90F;
                    default -> 0F;
                };
            }
            case SOUTH -> yaw = 180F;
            case WEST -> yaw = -90F;
            case EAST -> yaw = 90F;
            default -> yaw = 0F;


        }
        return new Pair<>(yaw, pitch);
    }
}
