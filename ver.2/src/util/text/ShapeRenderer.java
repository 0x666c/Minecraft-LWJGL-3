package util.text;

import static org.lwjgl.opengl.GL11.*;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

public final class ShapeRenderer {

	private FloatBuffer buffer = BufferUtils.createFloatBuffer(524288);
	private float[] data = new float[524288];
	private int vertices = 0;
	private float u;
	private float v;
	private float r;
	private float g;
	private float b;
	private boolean color = false;
	private boolean texture = false;
	private int vertexLength = 3;
	private int length = 0;
	private boolean noColor = false;
	public static ShapeRenderer instance = new ShapeRenderer();

	public final void end() {
		if (this.vertices > 0) {
			this.buffer.clear();
			this.buffer.put(this.data, 0, this.length);
			this.buffer.flip();
			if (this.texture && this.color) {
				GL11.glInterleavedArrays(GL_T2F_C3F_V3F, 0, this.buffer);
			} else if (this.texture) {
				GL11.glInterleavedArrays(GL_T2F_V3F, 0, this.buffer);
			} else if (this.color) {
				GL11.glInterleavedArrays(GL_C3F_V3F, 0, this.buffer);
			} else {
				GL11.glInterleavedArrays(GL_V3F, 0, this.buffer);
			}

			GL11.glDrawArrays(GL_QUADS, 0, this.vertices);
		}

		this.clear();
	}

	private void clear() {
		this.vertices = 0;
		this.buffer.clear();
		this.length = 0;
	}

	public final void begin() {
		this.clear();
		this.color = false;
		this.texture = false;
		this.noColor = false;
	}

	public final void color(float var1, float var2, float var3) {
		if (!this.noColor) {
			if (!this.color) {
				this.vertexLength += 3;
			}

			this.color = true;
			this.r = var1;
			this.g = var2;
			this.b = var3;
		}
	}

	public final void vertexUV(float x, float y, float z, float u, float v) {
		if (!this.texture) {
			this.vertexLength += 2;
		}
		this.texture = true;
		this.u = u;
		this.v = v;
		this.vertex(x, y, z);
	}

	public final void vertex(float x, float y, float z) {
		if (this.texture) {
			this.data[this.length++] = this.u;
			this.data[this.length++] = this.v;
		}

		if (this.color) {
			this.data[this.length++] = this.r;
			this.data[this.length++] = this.g;
			this.data[this.length++] = this.b;
		}

		this.data[this.length++] = x;
		this.data[this.length++] = y;
		this.data[this.length++] = z;
		++this.vertices;
		if (this.vertices % 4 == 0 && this.length >= 524288 - (this.vertexLength << 2)) {
			this.end();
		}

	}

	public final void color(int var1) {
		int var2 = var1 >> 16 & 255;
		int var3 = var1 >> 8 & 255;
		var1 &= 255;
		int var10001 = var2;
		int var10002 = var3;
		var3 = var1;
		var2 = var10002;
		var1 = var10001;
		byte var7 = (byte) var1;
		byte var4 = (byte) var2;
		byte var8 = (byte) var3;
		byte var6 = var4;
		byte var5 = var7;
		if (!this.noColor) {
			if (!this.color) {
				this.vertexLength += 3;
			}

			this.color = true;
			this.r = (float) (var5 & 255) / 255.0F;
			this.g = (float) (var6 & 255) / 255.0F;
			this.b = (float) (var8 & 255) / 255.0F;
		}

	}

	public final void noColor() {
		this.noColor = true;
	}

	public final void normal(float var1, float var2, float var3) {
		GL11.glNormal3f(var1, var2, var3);
	}

}