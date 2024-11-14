package sfiomn.legendary_additions.particles;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;

import javax.annotation.Nullable;

public class PoisonSmokeParticle extends TextureSheetParticle {
    protected final SpriteSet animatedSprite;
    private float angle;

    protected PoisonSmokeParticle(SpriteSet animatedSprite, ClientLevel level, double x, double y, double z, double xd, double yd, double zd) {
        super(level, x, y, z, xd, yd, zd);

        this.setSpriteFromAge(animatedSprite);
        this.animatedSprite = animatedSprite;

        this.angle = this.random.nextFloat() * ((float) Math.PI * 2F);

        //  Duration of particle in ticks
        this.lifetime = this.random.nextInt(20) + 20;

        //  Scaling
        this.quadSize *= (2.0F + this.random.nextFloat() * 0.5);

        //  Motion of particles
        this.xd = xd;
        this.yd = yd + (this.random.nextDouble() * 0.02D);
        this.zd = zd;

        //  Color
        this.rCol = 1f;
        this.gCol = 1f;
        this.bCol = 1f;

        this.hasPhysics = true;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.age % 5 == 0) {
            this.angle = (float) Math.random() * ((float) Math.PI * 2F);
        }
        this.xd += Math.cos(this.angle) * 0.0005;
        this.zd += Math.sin(this.angle) * 0.0005;
        this.setSpriteFromAge(this.animatedSprite);
        fadeOut();
    }

    private void fadeOut() {
        if (((float) age / lifetime) < 0.5f) {
            this.alpha = 1;
        } else {
            this.alpha = 1 - (((float) age / lifetime - 0.5f) * 2.0f);
        }
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    public static class Factory implements ParticleProvider<SimpleParticleType> {

        private final SpriteSet animatedSprite;

        public Factory(SpriteSet animatedSprite) {
            this.animatedSprite = animatedSprite;
        }

        @Nullable
        @Override
        public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double xd, double yd, double zd) {
            PoisonSmokeParticle poisonSmokeParticle = new PoisonSmokeParticle(this.animatedSprite, level, x, y, z, xd, yd, zd);
            poisonSmokeParticle.pickSprite(this.animatedSprite);
            return poisonSmokeParticle;
        }
    }
}
