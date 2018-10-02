package cursedflames.lib.client;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.model.ModelLoader;

//TODO consistent polygon winding - figure out how to enable backface culling?
public class RenderUtil {
	// @formatter:off
	public static final Vec3d[] TetrahedronVertices = {
			new Vec3d(Math.sqrt(8.0D/9), 0, -1.0D/3),
			new Vec3d(-Math.sqrt(2.0D/9), Math.sqrt(2.0D/3), -1.0D/3),
			new Vec3d(-Math.sqrt(2.0D/9), -Math.sqrt(2.0D/3), -1.0D/3),
			new Vec3d(0, 0, 1)
	};
	public static final Vec3d[][] TetrahedronTriangles = {
			{ TetrahedronVertices[1], TetrahedronVertices[0], TetrahedronVertices[2] },
			{ TetrahedronVertices[0], TetrahedronVertices[3], TetrahedronVertices[2] },
			{ TetrahedronVertices[3], TetrahedronVertices[1], TetrahedronVertices[2] },
			{ TetrahedronVertices[1], TetrahedronVertices[3], TetrahedronVertices[0] }
	};
	private static final double rootThird = Math.sqrt(1.0D/3);
	public static final Vec3d[] CubeVertices = { 
			new Vec3d(-rootThird, -rootThird, -rootThird),
			new Vec3d(-rootThird, -rootThird, rootThird),
			new Vec3d(-rootThird, rootThird, -rootThird),
			new Vec3d(-rootThird, rootThird, rootThird),
			new Vec3d(rootThird, -rootThird, -rootThird),
			new Vec3d(rootThird, -rootThird, rootThird),
			new Vec3d(rootThird, rootThird, -rootThird),
			new Vec3d(rootThird, rootThird, rootThird),
	};
	public static final Vec3d[][] CubeQuads = {
			{CubeVertices[6], CubeVertices[2], CubeVertices[0], CubeVertices[4]},
			{CubeVertices[7], CubeVertices[5], CubeVertices[1], CubeVertices[3]},
			{CubeVertices[0], CubeVertices[1], CubeVertices[5], CubeVertices[4]},
			{CubeVertices[2], CubeVertices[3], CubeVertices[7], CubeVertices[6]},
			{CubeVertices[0], CubeVertices[1], CubeVertices[3], CubeVertices[2]},
			{CubeVertices[4], CubeVertices[5], CubeVertices[7], CubeVertices[6]}
	};
	public static final Vec3d[] OctahedronVertices = {
			new Vec3d(-1, 0, 0),
			new Vec3d(1, 0, 0),
			new Vec3d(0, -1, 0),
			new Vec3d(0, 1, 0),
			new Vec3d(0, 0, -1),
			new Vec3d(0, 0, 1)
	};
	public static final Vec3d[][] OctahedronTriangles = {
			{OctahedronVertices[0], OctahedronVertices[2], OctahedronVertices[4]},
			{OctahedronVertices[0], OctahedronVertices[2], OctahedronVertices[5]},
			{OctahedronVertices[0], OctahedronVertices[3], OctahedronVertices[4]},
			{OctahedronVertices[0], OctahedronVertices[3], OctahedronVertices[5]},
			{OctahedronVertices[1], OctahedronVertices[2], OctahedronVertices[4]},
			{OctahedronVertices[1], OctahedronVertices[2], OctahedronVertices[5]},
			{OctahedronVertices[1], OctahedronVertices[3], OctahedronVertices[4]},
			{OctahedronVertices[1], OctahedronVertices[3], OctahedronVertices[5]}
	};
	private static final double phi = (1+Math.sqrt(5))/2;
	public static final Vec3d[] DodecahedronVertices = {
			CubeVertices[0],
			CubeVertices[1],
			CubeVertices[2],
			CubeVertices[3],
			CubeVertices[4],
			CubeVertices[5],
			CubeVertices[6],
			CubeVertices[7],
			new Vec3d(0, -phi, -1/phi).scale(rootThird),
			new Vec3d(0, -phi, 1/phi).scale(rootThird),
			new Vec3d(0, phi, -1/phi).scale(rootThird),
			new Vec3d(0, phi, 1/phi).scale(rootThird),
			new Vec3d(-1/phi, 0, -phi).scale(rootThird),
			new Vec3d(1/phi, 0, -phi).scale(rootThird),
			new Vec3d(-1/phi, 0, phi).scale(rootThird),
			new Vec3d(1/phi, 0, phi).scale(rootThird),
			new Vec3d(-phi, -1/phi, 0).scale(rootThird),
			new Vec3d(-phi, 1/phi, 0).scale(rootThird),
			new Vec3d(phi, -1/phi, 0).scale(rootThird),
			new Vec3d(phi, 1/phi, 0).scale(rootThird)
	};
	public static final Vec3d[][] DodecahedronPolys = {
			{DodecahedronVertices[0], DodecahedronVertices[8], DodecahedronVertices[9], DodecahedronVertices[1], DodecahedronVertices[16]},
			{DodecahedronVertices[0], DodecahedronVertices[12], DodecahedronVertices[13], DodecahedronVertices[4], DodecahedronVertices[8]},
			{DodecahedronVertices[0], DodecahedronVertices[16], DodecahedronVertices[17], DodecahedronVertices[2], DodecahedronVertices[12]},
			{DodecahedronVertices[1], DodecahedronVertices[9], DodecahedronVertices[5], DodecahedronVertices[15], DodecahedronVertices[14]},
			{DodecahedronVertices[1], DodecahedronVertices[14], DodecahedronVertices[3], DodecahedronVertices[17], DodecahedronVertices[16]},
			{DodecahedronVertices[2], DodecahedronVertices[10], DodecahedronVertices[6], DodecahedronVertices[13], DodecahedronVertices[12]},
			{DodecahedronVertices[2], DodecahedronVertices[17], DodecahedronVertices[3], DodecahedronVertices[11], DodecahedronVertices[10]},
			{DodecahedronVertices[3], DodecahedronVertices[14], DodecahedronVertices[15], DodecahedronVertices[7], DodecahedronVertices[11]},
			{DodecahedronVertices[4], DodecahedronVertices[18], DodecahedronVertices[5], DodecahedronVertices[9], DodecahedronVertices[8]},
			{DodecahedronVertices[4], DodecahedronVertices[13], DodecahedronVertices[6], DodecahedronVertices[19], DodecahedronVertices[18]},
			{DodecahedronVertices[5], DodecahedronVertices[18], DodecahedronVertices[19], DodecahedronVertices[7], DodecahedronVertices[15]},
			{DodecahedronVertices[6], DodecahedronVertices[10], DodecahedronVertices[11], DodecahedronVertices[7], DodecahedronVertices[19]}
	};
	private static final double icosaScale = 1/(2*Math.sin(Math.PI*2/5));
	public static final Vec3d[] IcosahedronVertices = {
			new Vec3d(0, -1, -phi).scale(icosaScale),
			new Vec3d(0, 1, -phi).scale(icosaScale),
			new Vec3d(0, -1, phi).scale(icosaScale),
			new Vec3d(0, 1, phi).scale(icosaScale),
			new Vec3d(-1, -phi, 0).scale(icosaScale),
			new Vec3d(1, -phi, 0).scale(icosaScale),
			new Vec3d(-1, phi, 0).scale(icosaScale),
			new Vec3d(1, phi, 0).scale(icosaScale),
			new Vec3d(-phi, 0, -1).scale(icosaScale),
			new Vec3d(-phi, 0, 1).scale(icosaScale),
			new Vec3d(phi, 0, -1).scale(icosaScale),
			new Vec3d(phi, 0, 1).scale(icosaScale)
	};
	public static final Vec3d[][] IcosahedronTriangles = {
			{IcosahedronVertices[0], IcosahedronVertices[1], IcosahedronVertices[8]},
			{IcosahedronVertices[0], IcosahedronVertices[1], IcosahedronVertices[10]},
			{IcosahedronVertices[2], IcosahedronVertices[3], IcosahedronVertices[9]},
			{IcosahedronVertices[2], IcosahedronVertices[3], IcosahedronVertices[11]},
			{IcosahedronVertices[4], IcosahedronVertices[5], IcosahedronVertices[0]},
			{IcosahedronVertices[4], IcosahedronVertices[5], IcosahedronVertices[2]},
			{IcosahedronVertices[6], IcosahedronVertices[7], IcosahedronVertices[1]},
			{IcosahedronVertices[6], IcosahedronVertices[7], IcosahedronVertices[3]},
			{IcosahedronVertices[8], IcosahedronVertices[9], IcosahedronVertices[4]},
			{IcosahedronVertices[8], IcosahedronVertices[9], IcosahedronVertices[6]},
			{IcosahedronVertices[10], IcosahedronVertices[11], IcosahedronVertices[5]},
			{IcosahedronVertices[10], IcosahedronVertices[11], IcosahedronVertices[7]},
			{IcosahedronVertices[0], IcosahedronVertices[4], IcosahedronVertices[8]},
			{IcosahedronVertices[2], IcosahedronVertices[4], IcosahedronVertices[9]},
			{IcosahedronVertices[1], IcosahedronVertices[6], IcosahedronVertices[8]},
			{IcosahedronVertices[3], IcosahedronVertices[6], IcosahedronVertices[9]},
			{IcosahedronVertices[0], IcosahedronVertices[5], IcosahedronVertices[10]},
			{IcosahedronVertices[2], IcosahedronVertices[5], IcosahedronVertices[11]},
			{IcosahedronVertices[1], IcosahedronVertices[7], IcosahedronVertices[10]},
			{IcosahedronVertices[3], IcosahedronVertices[7], IcosahedronVertices[11]}
	};
	// @formatter:on
	// TODO clean up all these functions somehow
	public static void addQuadToBuffer(BufferBuilder buf, double x0, double y0, double z0,
			double x1, double y1, double z1, double x2, double y2, double z2, double x3, double y3,
			double z3, int r, int g, int b, int a) {
		addQuadToBuffer(buf, x0, y0, z0, x1, y1, z1, x2, y2, z2, x3, y3, z3, r, g, b, a,
				ModelLoader.White.INSTANCE);
	}

	public static void addQuadToBuffer(BufferBuilder buf, double x0, double y0, double z0,
			double x1, double y1, double z1, double x2, double y2, double z2, double x3, double y3,
			double z3, int r, int g, int b, int a, TextureAtlasSprite sprite) {
		float u = sprite.getMinU();
		float v = sprite.getMinV();
		float uM = sprite.getMaxU();
		float vM = sprite.getMaxV();
		buf.pos(x0, y0, z0).color(r, g, b, a).tex(u, v).lightmap(255, 255).endVertex();
		buf.pos(x1, y1, z1).color(r, g, b, a).tex(u, vM).lightmap(255, 255).endVertex();
		buf.pos(x2, y2, z2).color(r, g, b, a).tex(uM, vM).lightmap(255, 255).endVertex();
		buf.pos(x3, y3, z3).color(r, g, b, a).tex(uM, v).lightmap(255, 255).endVertex();
	}

	public static void addQuadToBuffer(BufferBuilder buf, Vec3d pos0, Vec3d pos1, Vec3d pos2,
			Vec3d pos3, int r, int g, int b, int a) {
		addQuadToBuffer(buf, pos0.x, pos0.y, pos0.z, pos1.x, pos1.y, pos1.z, pos2.x, pos2.y, pos2.z,
				pos3.x, pos3.y, pos3.z, r, g, b, a);
	}

	public static void addQuadToBuffer(BufferBuilder buf, Vec3d pos0, Vec3d pos1, Vec3d pos2,
			Vec3d pos3, int r, int g, int b, int a, TextureAtlasSprite sprite) {
		addQuadToBuffer(buf, pos0.x, pos0.y, pos0.z, pos1.x, pos1.y, pos1.z, pos2.x, pos2.y, pos2.z,
				pos3.x, pos3.y, pos3.z, r, g, b, a, sprite);
	}

	public static void addTriToBuffer(BufferBuilder buf, double x0, double y0, double z0, double x1,
			double y1, double z1, double x2, double y2, double z2, int r, int g, int b, int a) {
		// There doesn't seem to be a way to draw triangles with the FastTESR
		// VertexBuffer
		addTriToBuffer(buf, x0, y0, z0, x1, y1, z1, x2, y2, z2, r, g, b, a,
				ModelLoader.White.INSTANCE);
	}

	public static void addTriToBuffer(BufferBuilder buf, Vec3d pos0, Vec3d pos1, Vec3d pos2, int r,
			int g, int b, int a) {
		addTriToBuffer(buf, pos0.x, pos0.y, pos0.z, pos1.x, pos1.y, pos1.z, pos2.x, pos2.y, pos2.z,
				r, g, b, a);
	}

	public static void addTriToBuffer(BufferBuilder buf, double x0, double y0, double z0, double x1,
			double y1, double z1, double x2, double y2, double z2, int r, int g, int b, int a,
			TextureAtlasSprite sprite) {
		addQuadToBuffer(buf, x0, y0, z0, x1, y1, z1, x2, y2, z2, (x0+x2)/2, (y0+y2)/2, (z0+z2)/2, r,
				g, b, a, sprite);
	}

	public static void addTriToBuffer(BufferBuilder buf, Vec3d pos0, Vec3d pos1, Vec3d pos2, int r,
			int g, int b, int a, TextureAtlasSprite sprite) {
		addTriToBuffer(buf, pos0.x, pos0.y, pos0.z, pos1.x, pos1.y, pos1.z, pos2.x, pos2.y, pos2.z,
				r, g, b, a, sprite);
	}
}
