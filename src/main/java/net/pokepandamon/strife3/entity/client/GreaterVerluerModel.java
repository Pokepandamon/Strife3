package net.pokepandamon.strife3.entity.client;

import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.pokepandamon.strife3.entity.animation.Strife3Animations;
import net.pokepandamon.strife3.entity.custom.GreaterVerluerEntity;

// Made with Blockbench 4.11.2
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports
public class GreaterVerluerModel<T extends GreaterVerluerEntity> extends SinglePartEntityModel<T> {
	private final ModelPart body;
	private final ModelPart sub_0;
	private final ModelPart sub_1;
	private final ModelPart tail4;
	private final ModelPart tail1;

	public GreaterVerluerModel(ModelPart root) {
		this.body = root.getChild("body");
		this.sub_0 = body.getChild("body_sub_0");
		this.sub_1 = body.getChild("body_sub_1");
		this.tail4 = sub_1.getChild("tail4");
		this.tail1 = sub_0.getChild("tail1");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData tentacle1 = modelPartData.addChild("tentacle1", ModelPartBuilder.create(), ModelTransform.pivot(-3.8F, 23.0F, -5.0F));

		ModelPartData tentacle2 = modelPartData.addChild("tentacle2", ModelPartBuilder.create(), ModelTransform.pivot(1.3F, 23.0F, -5.0F));

		ModelPartData tentacle3 = modelPartData.addChild("tentacle3", ModelPartBuilder.create(), ModelTransform.pivot(6.3F, 23.0F, -5.0F));

		ModelPartData tentacle4 = modelPartData.addChild("tentacle4", ModelPartBuilder.create(), ModelTransform.pivot(-6.3F, 23.0F, 0.0F));

		ModelPartData tentacle5 = modelPartData.addChild("tentacle5", ModelPartBuilder.create(), ModelTransform.pivot(-1.3F, 23.0F, 0.0F));

		ModelPartData tentacle6 = modelPartData.addChild("tentacle6", ModelPartBuilder.create(), ModelTransform.pivot(3.8F, 23.0F, 0.0F));

		ModelPartData tentacle7 = modelPartData.addChild("tentacle7", ModelPartBuilder.create(), ModelTransform.pivot(-3.8F, 23.0F, 5.0F));

		ModelPartData tentacle8 = modelPartData.addChild("tentacle8", ModelPartBuilder.create(), ModelTransform.pivot(1.3F, 23.0F, 5.0F));

		ModelPartData tentacle9 = modelPartData.addChild("tentacle9", ModelPartBuilder.create(), ModelTransform.pivot(6.3F, 23.0F, 5.0F));

		ModelPartData body = modelPartData.addChild("body", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 16.0F, 0.0F));

		ModelPartData body_sub_1 = body.addChild("body_sub_1", ModelPartBuilder.create().uv(128, 15).mirrored().cuboid(-2.5F, -13.25F, -7.5F, 5.0F, 5.0F, 15.0F, new Dilation(0.0F)).mirrored(false)
		.uv(146, 8).mirrored().cuboid(3.5F, -12.25F, -9.5F, 2.0F, 3.0F, 2.0F, new Dilation(0.0F)).mirrored(false)
		.uv(142, 3).mirrored().cuboid(3.5F, -11.25F, -7.5F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F)).mirrored(false)
		.uv(128, 15).mirrored().cuboid(-4.5F, -11.25F, -7.5F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F)).mirrored(false)
		.uv(146, 0).mirrored().cuboid(4.5F, -12.25F, -11.5F, 2.0F, 3.0F, 2.0F, new Dilation(0.0F)).mirrored(false)
		.uv(158, 5).mirrored().cuboid(4.5F, -11.25F, -13.5F, 2.0F, 1.0F, 2.0F, new Dilation(0.0F)).mirrored(false)
		.uv(128, 3).mirrored().cuboid(4.5F, -11.25F, -16.5F, 1.0F, 1.0F, 3.0F, new Dilation(0.0F)).mirrored(false)
		.uv(134, 0).mirrored().cuboid(3.5F, -11.25F, -18.5F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F)).mirrored(false)
		.uv(136, 3).mirrored().cuboid(-5.5F, -11.25F, -16.5F, 1.0F, 1.0F, 3.0F, new Dilation(0.0F)).mirrored(false)
		.uv(128, 0).mirrored().cuboid(-4.5F, -11.25F, -18.5F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F)).mirrored(false)
		.uv(154, 8).mirrored().cuboid(-5.5F, -12.25F, -9.5F, 2.0F, 3.0F, 2.0F, new Dilation(0.0F)).mirrored(false)
		.uv(154, 0).mirrored().cuboid(-6.5F, -12.25F, -11.5F, 2.0F, 3.0F, 2.0F, new Dilation(0.0F)).mirrored(false)
		.uv(139, 15).mirrored().cuboid(-6.5F, -11.25F, -9.5F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F)).mirrored(false)
		.uv(140, 0).mirrored().cuboid(-5.5F, -12.25F, -13.5F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F)).mirrored(false)
		.uv(140, 8).mirrored().cuboid(-4.5F, -11.25F, -11.5F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F)).mirrored(false)
		.uv(144, 5).mirrored().cuboid(3.5F, -11.25F, -11.5F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F)).mirrored(false)
		.uv(138, 11).mirrored().cuboid(1.5F, -11.25F, -9.5F, 2.0F, 1.0F, 2.0F, new Dilation(0.0F)).mirrored(false)
		.uv(152, 13).mirrored().cuboid(1.5F, -12.25F, -8.5F, 2.0F, 1.0F, 1.0F, new Dilation(0.0F)).mirrored(false)
		.uv(134, 14).mirrored().cuboid(1.5F, -10.25F, -8.5F, 2.0F, 1.0F, 1.0F, new Dilation(0.0F)).mirrored(false)
		.uv(128, 13).mirrored().cuboid(-3.5F, -10.25F, -8.5F, 2.0F, 1.0F, 1.0F, new Dilation(0.0F)).mirrored(false)
		.uv(146, 13).mirrored().cuboid(-3.5F, -12.25F, -8.5F, 2.0F, 1.0F, 1.0F, new Dilation(0.0F)).mirrored(false)
		.uv(160, 0).mirrored().cuboid(-1.5F, -11.25F, -8.5F, 3.0F, 1.0F, 1.0F, new Dilation(0.0F)).mirrored(false)
		.uv(128, 10).mirrored().cuboid(-3.5F, -11.25F, -9.5F, 2.0F, 1.0F, 2.0F, new Dilation(0.0F)).mirrored(false)
		.uv(134, 7).mirrored().cuboid(-5.5F, -10.25F, -13.5F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F)).mirrored(false)
		.uv(128, 7).mirrored().cuboid(4.5F, -10.25F, -13.5F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F)).mirrored(false)
		.uv(133, 3).mirrored().cuboid(4.5F, -12.25F, -13.5F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F)).mirrored(false)
		.uv(162, 2).mirrored().cuboid(5.5F, -11.25F, -9.5F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F)).mirrored(false)
		.uv(150, 5).mirrored().cuboid(-6.5F, -11.25F, -13.5F, 2.0F, 1.0F, 2.0F, new Dilation(0.0F)).mirrored(false)
		.uv(153, 0).mirrored().cuboid(2.5F, -12.25F, -7.5F, 1.0F, 3.0F, 15.0F, new Dilation(0.0F)).mirrored(false)
		.uv(170, 3).mirrored().cuboid(-3.5F, -12.25F, -7.5F, 1.0F, 3.0F, 15.0F, new Dilation(0.0F)).mirrored(false)
		.uv(153, 20).mirrored().cuboid(-3.5F, -13.25F, -4.5F, 1.0F, 1.0F, 9.0F, new Dilation(0.0F)).mirrored(false)
		.uv(154, 25).mirrored().cuboid(-3.5F, -14.25F, -3.5F, 1.0F, 1.0F, 3.0F, new Dilation(0.0F)).mirrored(false)
		.uv(188, 13).mirrored().cuboid(2.5F, -14.25F, -3.5F, 1.0F, 1.0F, 3.0F, new Dilation(0.0F)).mirrored(false)
		.uv(171, 8).mirrored().cuboid(-3.5F, -9.25F, -1.5F, 1.0F, 1.0F, 6.0F, new Dilation(0.0F)).mirrored(false)
		.uv(171, 0).mirrored().cuboid(2.5F, -9.25F, -1.5F, 1.0F, 1.0F, 6.0F, new Dilation(0.0F)).mirrored(false)
		.uv(187, 8).mirrored().cuboid(2.5F, -13.25F, -4.5F, 1.0F, 1.0F, 9.0F, new Dilation(0.0F)).mirrored(false)
		.uv(192, 28).mirrored().cuboid(-4.5F, -11.25F, -1.5F, 1.0F, 1.0F, 6.0F, new Dilation(0.0F)).mirrored(false)
		.uv(129, 23).mirrored().cuboid(3.5F, -11.25F, -1.5F, 1.0F, 1.0F, 6.0F, new Dilation(0.0F)).mirrored(false)
		.uv(187, 0).mirrored().cuboid(-5.5F, -11.25F, -0.5F, 1.0F, 1.0F, 6.0F, new Dilation(0.0F)).mirrored(false)
		.uv(129, 16).mirrored().cuboid(4.5F, -11.25F, -0.5F, 1.0F, 1.0F, 6.0F, new Dilation(0.0F)).mirrored(false)
		.uv(192, 21).mirrored().cuboid(-6.5F, -11.25F, 1.5F, 1.0F, 1.0F, 6.0F, new Dilation(0.0F)).mirrored(false)
		.uv(168, 21).mirrored().cuboid(5.5F, -11.25F, 1.5F, 1.0F, 1.0F, 6.0F, new Dilation(0.0F)).mirrored(false)
		.uv(168, 21).mirrored().cuboid(-2.5F, -14.25F, -6.5F, 5.0F, 1.0F, 14.0F, new Dilation(0.0F)).mirrored(false)
		.uv(128, 49).mirrored().cuboid(-2.5F, -8.25F, -6.5F, 5.0F, 1.0F, 14.0F, new Dilation(0.0F)).mirrored(false)
		.uv(128, 35).mirrored().cuboid(-1.5F, -16.25F, -5.5F, 3.0F, 2.0F, 12.0F, new Dilation(0.0F)).mirrored(false)
		.uv(158, 36).mirrored().cuboid(-1.5F, -7.25F, -5.5F, 3.0F, 2.0F, 12.0F, new Dilation(0.0F)).mirrored(false)
		.uv(220, 19).mirrored().cuboid(-1.5F, -18.25F, -4.5F, 3.0F, 2.0F, 12.0F, new Dilation(0.0F)).mirrored(false)
		.uv(166, 50).mirrored().cuboid(-1.5F, -5.25F, -4.5F, 3.0F, 2.0F, 12.0F, new Dilation(0.0F)).mirrored(false)
		.uv(220, 33).mirrored().cuboid(-0.5F, -19.25F, -3.5F, 1.0F, 1.0F, 12.0F, new Dilation(0.0F)).mirrored(false)
		.uv(188, 36).mirrored().cuboid(-0.5F, -3.25F, -3.5F, 1.0F, 1.0F, 12.0F, new Dilation(0.0F)).mirrored(false)
		.uv(202, 48).mirrored().cuboid(-0.5F, -20.25F, -2.5F, 1.0F, 1.0F, 12.0F, new Dilation(0.0F)).mirrored(false)
		.uv(184, 49).mirrored().cuboid(-0.5F, -2.25F, -2.5F, 1.0F, 1.0F, 12.0F, new Dilation(0.0F)).mirrored(false)
		.uv(214, 48).mirrored().cuboid(-0.5F, -21.25F, -2.5F, 1.0F, 1.0F, 14.0F, new Dilation(0.0F)).mirrored(false)
		.uv(198, 0).mirrored().cuboid(-0.5F, -1.25F, -2.5F, 1.0F, 1.0F, 14.0F, new Dilation(0.0F)).mirrored(false)
		.uv(224, 63).mirrored().cuboid(-0.5F, -22.25F, -0.5F, 1.0F, 1.0F, 15.0F, new Dilation(0.0F)).mirrored(false)
		.uv(200, 15).mirrored().cuboid(-0.5F, -0.25F, -0.5F, 1.0F, 1.0F, 15.0F, new Dilation(0.0F)).mirrored(false)
		.uv(206, 63).mirrored().cuboid(-0.5F, -23.25F, 2.5F, 1.0F, 1.0F, 16.0F, new Dilation(0.0F)).mirrored(false)
		.uv(202, 31).mirrored().cuboid(-0.5F, 0.75F, 2.5F, 1.0F, 1.0F, 16.0F, new Dilation(0.0F)).mirrored(false)
		.uv(188, 64).mirrored().cuboid(-0.5F, -24.25F, 7.5F, 1.0F, 1.0F, 16.0F, new Dilation(0.0F)).mirrored(false)
		.uv(217, 0).mirrored().cuboid(-0.5F, 1.75F, 7.5F, 1.0F, 1.0F, 16.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(0.0F, 8.0F, 0.0F));

		ModelPartData tail4 = body_sub_1.addChild("tail4", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -10.75F, 7.5F));

		ModelPartData body_sub_2 = tail4.addChild("body_sub_2", ModelPartBuilder.create().uv(128, 64).mirrored().cuboid(1.5F, -1.5F, 0.0F, 1.0F, 3.0F, 7.0F, new Dilation(0.0F)).mirrored(false)
		.uv(158, 50).mirrored().cuboid(-1.5F, -2.5F, 0.0F, 3.0F, 1.0F, 7.0F, new Dilation(0.0F)).mirrored(false)
		.uv(144, 64).mirrored().cuboid(-1.5F, 1.5F, 0.0F, 3.0F, 1.0F, 7.0F, new Dilation(0.0F)).mirrored(false)
		.uv(206, 63).mirrored().cuboid(-2.5F, -1.5F, 0.0F, 1.0F, 3.0F, 7.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData tail5 = tail4.addChild("tail5", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 7.0F));

		ModelPartData body_sub_4 = tail5.addChild("body_sub_4", ModelPartBuilder.create().uv(203, 0).mirrored().cuboid(1.5F, -0.5F, 0.0F, 1.0F, 1.0F, 3.0F, new Dilation(0.0F)).mirrored(false)
		.uv(154, 64).mirrored().cuboid(-1.5F, -1.5F, -7.0F, 3.0F, 3.0F, 14.0F, new Dilation(0.0F)).mirrored(false)
		.uv(200, 8).mirrored().cuboid(-0.5F, -2.5F, 0.0F, 1.0F, 1.0F, 5.0F, new Dilation(0.0F)).mirrored(false)
		.uv(195, 0).mirrored().cuboid(-2.5F, -0.5F, 0.0F, 1.0F, 1.0F, 3.0F, new Dilation(0.0F)).mirrored(false)
		.uv(214, 0).mirrored().cuboid(-0.5F, 1.5F, 0.0F, 1.0F, 1.0F, 5.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData tail6 = tail5.addChild("tail6", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 7.0F));

		ModelPartData body_sub_6 = tail6.addChild("body_sub_6", ModelPartBuilder.create().uv(235, 0).mirrored().cuboid(-0.5F, -1.5F, 0.0F, 1.0F, 1.0F, 6.0F, new Dilation(0.0F)).mirrored(false)
		.uv(217, 17).mirrored().cuboid(0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 4.0F, new Dilation(0.0F)).mirrored(false)
		.uv(238, 17).mirrored().cuboid(1.5F, -0.5F, 4.0F, 1.0F, 1.0F, 4.0F, new Dilation(0.0F)).mirrored(false)
		.uv(225, 4).mirrored().cuboid(0.5F, -0.5F, 4.0F, 1.0F, 1.0F, 3.0F, new Dilation(0.0F)).mirrored(false)
		.uv(128, 72).mirrored().cuboid(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 17.0F, new Dilation(0.0F)).mirrored(false)
		.uv(225, 0).mirrored().cuboid(-1.5F, -0.5F, 4.0F, 1.0F, 1.0F, 3.0F, new Dilation(0.0F)).mirrored(false)
		.uv(205, 20).mirrored().cuboid(-2.5F, -0.5F, 4.0F, 1.0F, 1.0F, 4.0F, new Dilation(0.0F)).mirrored(false)
		.uv(238, 22).mirrored().cuboid(-3.5F, -0.5F, 5.0F, 1.0F, 1.0F, 4.0F, new Dilation(0.0F)).mirrored(false)
		.uv(208, 31).mirrored().cuboid(-4.5F, -0.5F, 7.0F, 1.0F, 1.0F, 4.0F, new Dilation(0.0F)).mirrored(false)
		.uv(214, 6).mirrored().cuboid(-1.5F, -0.5F, 0.0F, 1.0F, 1.0F, 4.0F, new Dilation(0.0F)).mirrored(false)
		.uv(235, 7).mirrored().cuboid(-0.5F, 0.5F, 0.0F, 1.0F, 1.0F, 6.0F, new Dilation(0.0F)).mirrored(false)
		.uv(217, 22).mirrored().cuboid(2.5F, -0.5F, 5.0F, 1.0F, 1.0F, 4.0F, new Dilation(0.0F)).mirrored(false)
		.uv(205, 25).mirrored().cuboid(3.5F, -0.5F, 7.0F, 1.0F, 1.0F, 4.0F, new Dilation(0.0F)).mirrored(false)
		.uv(128, 35).mirrored().cuboid(0.5F, -0.5F, 14.0F, 1.0F, 1.0F, 3.0F, new Dilation(0.0F)).mirrored(false)
		.uv(146, 35).mirrored().cuboid(1.5F, -0.5F, 14.0F, 1.0F, 1.0F, 4.0F, new Dilation(0.0F)).mirrored(false)
		.uv(146, 40).mirrored().cuboid(2.5F, -0.5F, 15.0F, 1.0F, 1.0F, 4.0F, new Dilation(0.0F)).mirrored(false)
		.uv(156, 35).mirrored().cuboid(3.5F, -0.5F, 17.0F, 1.0F, 1.0F, 4.0F, new Dilation(0.0F)).mirrored(false)
		.uv(128, 39).mirrored().cuboid(-1.5F, -0.5F, 14.0F, 1.0F, 1.0F, 3.0F, new Dilation(0.0F)).mirrored(false)
		.uv(156, 40).mirrored().cuboid(-2.5F, -0.5F, 14.0F, 1.0F, 1.0F, 4.0F, new Dilation(0.0F)).mirrored(false)
		.uv(176, 36).mirrored().cuboid(-3.5F, -0.5F, 15.0F, 1.0F, 1.0F, 4.0F, new Dilation(0.0F)).mirrored(false)
		.uv(176, 41).mirrored().cuboid(-4.5F, -0.5F, 17.0F, 1.0F, 1.0F, 4.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData body_sub_0 = body.addChild("body_sub_0", ModelPartBuilder.create().uv(78, 34).mirrored().cuboid(-4.5F, -14.25F, -6.5F, 9.0F, 9.0F, 15.0F, new Dilation(0.0F)).mirrored(false)
		.uv(70, 105).mirrored().cuboid(4.5F, -8.25F, -5.5F, 1.0F, 1.0F, 13.0F, new Dilation(0.0F)).mirrored(false)
		.uv(104, 106).mirrored().cuboid(5.5F, -8.25F, -3.5F, 1.0F, 1.0F, 11.0F, new Dilation(0.0F)).mirrored(false)
		.uv(110, 0).mirrored().cuboid(6.5F, -8.25F, -1.5F, 1.0F, 1.0F, 8.0F, new Dilation(0.0F)).mirrored(false)
		.uv(30, 116).mirrored().cuboid(-6.5F, -8.25F, -3.5F, 1.0F, 1.0F, 11.0F, new Dilation(0.0F)).mirrored(false)
		.uv(0, 114).mirrored().cuboid(-5.5F, -8.25F, -5.5F, 1.0F, 1.0F, 13.0F, new Dilation(0.0F)).mirrored(false)
		.uv(0, 0).mirrored().cuboid(-7.5F, -8.25F, -1.5F, 1.0F, 1.0F, 8.0F, new Dilation(0.0F)).mirrored(false)
		.uv(4, 48).mirrored().cuboid(3.5F, -13.25F, -7.5F, 1.0F, 7.0F, 1.0F, new Dilation(0.0F)).mirrored(false)
		.uv(81, 1).mirrored().cuboid(2.5F, -8.25F, -8.5F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F)).mirrored(false)
		.uv(103, 4).mirrored().cuboid(2.5F, -12.25F, -8.5F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F)).mirrored(false)
		.uv(93, 1).mirrored().cuboid(-3.5F, -8.25F, -8.5F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F)).mirrored(false)
		.uv(114, 12).mirrored().cuboid(-3.5F, -12.25F, -8.5F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F)).mirrored(false)
		.uv(0, 48).mirrored().cuboid(-4.5F, -13.25F, -7.5F, 1.0F, 7.0F, 1.0F, new Dilation(0.0F)).mirrored(false)
		.uv(0, 59).mirrored().cuboid(-1.5F, -14.25F, -21.5F, 3.0F, 1.0F, 4.0F, new Dilation(0.0F)).mirrored(false)
		.uv(99, 58).mirrored().cuboid(-3.5F, -14.25F, -10.5F, 7.0F, 2.0F, 4.0F, new Dilation(0.0F)).mirrored(false)
		.uv(28, 55).mirrored().cuboid(-2.5F, -15.25F, -17.5F, 5.0F, 2.0F, 7.0F, new Dilation(0.0F)).mirrored(false)
		.uv(52, 55).mirrored().cuboid(-2.5F, -6.25F, -17.5F, 5.0F, 2.0F, 7.0F, new Dilation(0.0F)).mirrored(false)
		.uv(14, 59).mirrored().cuboid(-1.5F, -6.25F, -21.5F, 3.0F, 1.0F, 4.0F, new Dilation(0.0F)).mirrored(false)
		.uv(14, 61).mirrored().cuboid(-1.5F, -7.25F, -20.5F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F)).mirrored(false)
		.uv(0, 59).mirrored().cuboid(-1.5F, -7.25F, -18.5F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F)).mirrored(false)
		.uv(24, 61).mirrored().cuboid(0.5F, -7.25F, -20.5F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F)).mirrored(false)
		.uv(10, 59).mirrored().cuboid(0.5F, -7.25F, -18.5F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F)).mirrored(false)
		.uv(31, 58).mirrored().cuboid(-2.5F, -7.25F, -16.5F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F)).mirrored(false)
		.uv(4, 56).mirrored().cuboid(-2.5F, -8.25F, -14.5F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F)).mirrored(false)
		.uv(20, 56).mirrored().cuboid(-2.5F, -8.25F, -12.5F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F)).mirrored(false)
		.uv(16, 56).mirrored().cuboid(1.5F, -8.25F, -12.5F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F)).mirrored(false)
		.uv(0, 56).mirrored().cuboid(1.5F, -8.25F, -14.5F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F)).mirrored(false)
		.uv(31, 60).mirrored().cuboid(1.5F, -7.25F, -16.5F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F)).mirrored(false)
		.uv(69, 59).mirrored().cuboid(-3.5F, -9.25F, -10.5F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F)).mirrored(false)
		.uv(73, 59).mirrored().cuboid(2.5F, -9.25F, -10.5F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F)).mirrored(false)
		.uv(53, 59).mirrored().cuboid(2.5F, -12.25F, -10.5F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F)).mirrored(false)
		.uv(49, 59).mirrored().cuboid(-3.5F, -12.25F, -10.5F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F)).mirrored(false)
		.uv(24, 56).mirrored().cuboid(-2.5F, -13.25F, -12.5F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F)).mirrored(false)
		.uv(45, 59).mirrored().cuboid(1.5F, -13.25F, -12.5F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F)).mirrored(false)
		.uv(12, 56).mirrored().cuboid(1.5F, -13.25F, -14.5F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F)).mirrored(false)
		.uv(8, 56).mirrored().cuboid(-2.5F, -13.25F, -14.5F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F)).mirrored(false)
		.uv(31, 56).mirrored().cuboid(-2.5F, -13.25F, -16.5F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F)).mirrored(false)
		.uv(31, 54).mirrored().cuboid(1.5F, -13.25F, -16.5F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F)).mirrored(false)
		.uv(14, 59).mirrored().cuboid(0.5F, -13.25F, -18.5F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F)).mirrored(false)
		.uv(24, 59).mirrored().cuboid(-1.5F, -13.25F, -18.5F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F)).mirrored(false)
		.uv(0, 61).mirrored().cuboid(0.5F, -13.25F, -20.5F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F)).mirrored(false)
		.uv(10, 61).mirrored().cuboid(-1.5F, -13.25F, -20.5F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F)).mirrored(false)
		.uv(77, 58).mirrored().cuboid(-3.5F, -7.25F, -10.5F, 7.0F, 2.0F, 4.0F, new Dilation(0.0F)).mirrored(false)
		.uv(0, 28).mirrored().cuboid(-3.5F, -15.25F, -10.5F, 7.0F, 1.0F, 19.0F, new Dilation(0.0F)).mirrored(false)
		.uv(45, 5).mirrored().cuboid(-3.5F, -5.25F, -10.5F, 7.0F, 1.0F, 19.0F, new Dilation(0.0F)).mirrored(false)
		.uv(34, 25).mirrored().cuboid(-2.5F, -16.25F, -12.5F, 5.0F, 1.0F, 21.0F, new Dilation(0.0F)).mirrored(false)
		.uv(76, 12).mirrored().cuboid(-2.5F, -4.25F, -12.5F, 5.0F, 1.0F, 21.0F, new Dilation(0.0F)).mirrored(false)
		.uv(33, 36).mirrored().cuboid(-0.5F, -18.25F, -6.5F, 1.0F, 2.0F, 8.0F, new Dilation(0.0F)).mirrored(false)
		.uv(111, 41).mirrored().cuboid(-0.5F, -17.25F, 1.5F, 1.0F, 1.0F, 7.0F, new Dilation(0.0F)).mirrored(false)
		.uv(3, 29).mirrored().cuboid(-0.5F, -3.25F, 1.5F, 1.0F, 1.0F, 7.0F, new Dilation(0.0F)).mirrored(false)
		.uv(0, 31).mirrored().cuboid(-0.5F, -17.25F, -10.5F, 1.0F, 1.0F, 4.0F, new Dilation(0.0F)).mirrored(false)
		.uv(8, 51).mirrored().cuboid(-0.5F, -3.25F, -10.5F, 1.0F, 1.0F, 4.0F, new Dilation(0.0F)).mirrored(false)
		.uv(1, 37).mirrored().cuboid(-0.5F, -3.25F, -6.5F, 1.0F, 2.0F, 8.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(0.0F, 8.0F, 0.0F));

		ModelPartData tail1 = body_sub_0.addChild("tail1", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -9.75F, 8.5F));

		ModelPartData body_sub_9 = tail1.addChild("body_sub_9", ModelPartBuilder.create().uv(79, 72).mirrored().cuboid(-0.5F, -10.5F, 10.0F, 1.0F, 1.0F, 5.0F, new Dilation(0.0F)).mirrored(false)
		.uv(61, 69).mirrored().cuboid(-0.5F, -9.5F, 6.0F, 1.0F, 1.0F, 9.0F, new Dilation(0.0F)).mirrored(false)
		.uv(74, 81).mirrored().cuboid(-0.5F, -8.5F, 5.0F, 1.0F, 1.0F, 10.0F, new Dilation(0.0F)).mirrored(false)
		.uv(100, 82).mirrored().cuboid(-0.5F, -7.5F, 3.0F, 1.0F, 1.0F, 12.0F, new Dilation(0.0F)).mirrored(false)
		.uv(39, 83).mirrored().cuboid(-0.5F, -6.5F, 0.0F, 1.0F, 1.0F, 15.0F, new Dilation(0.0F)).mirrored(false)
		.uv(40, 64).mirrored().cuboid(-1.5F, -5.5F, 0.0F, 3.0F, 1.0F, 15.0F, new Dilation(0.0F)).mirrored(false)
		.uv(0, 64).mirrored().cuboid(-2.5F, -4.5F, 0.0F, 5.0F, 1.0F, 15.0F, new Dilation(0.0F)).mirrored(false)
		.uv(20, 2).mirrored().cuboid(-3.5F, -3.5F, 0.0F, 7.0F, 7.0F, 15.0F, new Dilation(0.0F)).mirrored(false)
		.uv(107, 25).mirrored().cuboid(-4.5F, -1.5F, 5.0F, 1.0F, 3.0F, 5.0F, new Dilation(0.0F)).mirrored(false)
		.uv(77, 36).mirrored().cuboid(-4.5F, -2.5F, 0.0F, 1.0F, 5.0F, 5.0F, new Dilation(0.0F)).mirrored(false)
		.uv(76, 64).mirrored().cuboid(-2.5F, 3.5F, 0.0F, 5.0F, 1.0F, 15.0F, new Dilation(0.0F)).mirrored(false)
		.uv(0, 80).mirrored().cuboid(-1.5F, 4.5F, 0.0F, 3.0F, 1.0F, 15.0F, new Dilation(0.0F)).mirrored(false)
		.uv(25, 70).mirrored().cuboid(-0.5F, 5.5F, 0.0F, 1.0F, 1.0F, 8.0F, new Dilation(0.0F)).mirrored(false)
		.uv(43, 25).mirrored().cuboid(3.5F, -2.5F, 0.0F, 1.0F, 5.0F, 5.0F, new Dilation(0.0F)).mirrored(false)
		.uv(65, 38).mirrored().cuboid(3.5F, -1.5F, 5.0F, 1.0F, 3.0F, 5.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData tail2 = tail1.addChild("tail2", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 15.0F));

		ModelPartData body_sub_11 = tail2.addChild("body_sub_11", ModelPartBuilder.create().uv(63, 77).mirrored().cuboid(-0.5F, -9.5F, 0.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F)).mirrored(false)
		.uv(63, 77).mirrored().cuboid(-0.5F, -8.5F, 0.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F)).mirrored(false)
		.uv(98, 92).mirrored().cuboid(-0.5F, -7.5F, 0.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F)).mirrored(false)
		.uv(36, 84).mirrored().cuboid(-0.5F, -6.5F, 0.0F, 1.0F, 1.0F, 3.0F, new Dilation(0.0F)).mirrored(false)
		.uv(79, 66).mirrored().cuboid(-0.5F, -5.5F, 0.0F, 1.0F, 1.0F, 5.0F, new Dilation(0.0F)).mirrored(false)
		.uv(21, 86).mirrored().cuboid(-0.5F, -4.5F, 0.0F, 1.0F, 1.0F, 8.0F, new Dilation(0.0F)).mirrored(false)
		.uv(74, 92).mirrored().cuboid(-1.5F, -3.5F, 0.0F, 3.0F, 1.0F, 11.0F, new Dilation(0.0F)).mirrored(false)
		.uv(0, 96).mirrored().cuboid(-2.5F, -2.5F, 0.0F, 5.0F, 5.0F, 11.0F, new Dilation(0.0F)).mirrored(false)
		.uv(56, 85).mirrored().cuboid(-2.5F, -2.5F, 11.0F, 5.0F, 5.0F, 2.0F, new Dilation(0.0F)).mirrored(false)
		.uv(21, 102).mirrored().cuboid(-2.5F, -1.5F, 13.0F, 5.0F, 3.0F, 2.0F, new Dilation(0.0F)).mirrored(false)
		.uv(87, 80).mirrored().cuboid(2.5F, -0.5F, 11.0F, 2.0F, 1.0F, 4.0F, new Dilation(0.0F)).mirrored(false)
		.uv(56, 80).mirrored().cuboid(2.5F, -0.5F, 7.0F, 1.0F, 1.0F, 4.0F, new Dilation(0.0F)).mirrored(false)
		.uv(46, 80).mirrored().cuboid(2.5F, -0.5F, 3.0F, 1.0F, 1.0F, 3.0F, new Dilation(0.0F)).mirrored(false)
		.uv(46, 89).mirrored().cuboid(2.5F, -1.5F, 0.0F, 1.0F, 3.0F, 3.0F, new Dilation(0.0F)).mirrored(false)
		.uv(56, 92).mirrored().cuboid(-4.5F, -0.5F, 11.0F, 2.0F, 1.0F, 4.0F, new Dilation(0.0F)).mirrored(false)
		.uv(66, 80).mirrored().cuboid(-3.5F, -0.5F, 7.0F, 1.0F, 1.0F, 4.0F, new Dilation(0.0F)).mirrored(false)
		.uv(47, 70).mirrored().cuboid(-3.5F, -0.5F, 3.0F, 1.0F, 1.0F, 3.0F, new Dilation(0.0F)).mirrored(false)
		.uv(47, 64).mirrored().cuboid(-3.5F, -1.5F, 0.0F, 1.0F, 3.0F, 3.0F, new Dilation(0.0F)).mirrored(false)
		.uv(32, 99).mirrored().cuboid(-1.5F, 2.5F, 0.0F, 3.0F, 1.0F, 11.0F, new Dilation(0.0F)).mirrored(false)
		.uv(36, 80).mirrored().cuboid(-0.5F, 3.5F, 0.0F, 1.0F, 1.0F, 8.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData tail3 = tail2.addChild("tail3", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 15.0F));

		ModelPartData body_sub_13 = tail3.addChild("body_sub_13", ModelPartBuilder.create().uv(98, 95).mirrored().cuboid(-5.5F, -0.5F, 0.0F, 11.0F, 1.0F, 4.0F, new Dilation(0.0F)).mirrored(false)
		.uv(102, 100).mirrored().cuboid(0.5F, -0.5F, 4.0F, 6.0F, 1.0F, 4.0F, new Dilation(0.0F)).mirrored(false)
		.uv(49, 105).mirrored().cuboid(-6.5F, -0.5F, 4.0F, 6.0F, 1.0F, 4.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
		return TexturedModelData.of(modelData, 256, 128);
	}
	@Override
	public void setAngles(GreaterVerluerEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.getPart().traverse().forEach(ModelPart::resetTransform);

		this.animateMovement(Strife3Animations.swim, limbSwing, limbSwingAmount, 2F, 2.5F);
		this.updateAnimation(entity.idleAnimationState, Strife3Animations.swim, ageInTicks, 1F);
	}


	//@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		/*tentacle1.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		tentacle2.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		tentacle3.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		tentacle4.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		tentacle5.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		tentacle6.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		tentacle7.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		tentacle8.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		tentacle9.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);*/
		body.render(matrices, vertexConsumer, light, overlay);
	}

	@Override
	public ModelPart getPart() {
		return body;
	}

	/*@Override
	public void setAngles(GreaterVerluerEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {

	}*/
}