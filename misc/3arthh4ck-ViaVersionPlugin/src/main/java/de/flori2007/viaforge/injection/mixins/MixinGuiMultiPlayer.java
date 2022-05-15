package de.flori2007.viaforge.injection.mixins;

import com.github.creeper123123321.viafabric.ViaFabric;
import com.github.creeper123123321.viafabric.util.ProtocolUtils;
import de.flori2007.viaforge.utils.ProtocolSorter;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import us.myles.ViaVersion.api.protocol.ProtocolVersion;

@Mixin(GuiMultiplayer.class)
public abstract class MixinGuiMultiPlayer extends GuiScreen
{
    private GuiButton viaVersionButton;

    private int index = ProtocolSorter.getProtocolVersions().indexOf(ProtocolVersion.v1_12_2);

    @Inject(
        method = "createButtons",
        at = @At("HEAD"))
    public void createButtonsHook(CallbackInfo info)
    {
        ViaFabric.clientSideVersion = ProtocolSorter
                                        .getProtocolVersions()
                                        .get(index).getVersion();
        viaVersionButton = addButton(new GuiButton(1335, 27, 5, 100, 20,
                ProtocolSorter.getProtocolVersions().get(index).getName()));
        this.buttonList.add(new GuiButton(1333, 2, 5, 20, 20, "<"));
        this.buttonList.add(new GuiButton(1331, 132, 5, 20, 20, ">"));
    }

    @Inject(
        method = "actionPerformed",
        at = @At("HEAD"),
        cancellable = true)
    protected void actionPerformed(GuiButton button, CallbackInfo info)
    {
        if (button.enabled)
        {
            if (button.id == 1333)
            {
                index++;
                if (index >= ProtocolSorter.getProtocolVersions().size())
                {
                    index = ProtocolSorter.getProtocolVersions().size() - 1;
                    return;
                }

                ViaFabric.clientSideVersion =
                        ProtocolSorter.getProtocolVersions()
                                      .get(index)
                                      .getVersion();

                viaVersionButton.displayString = ProtocolUtils
                        .getProtocolName(ViaFabric.clientSideVersion);
                info.cancel();
            }
            else if (button.id == 1331)
            {
                index--;
                if (index < 0) {
                    index = 0;
                    return;
                }

                ViaFabric.clientSideVersion =
                        ProtocolSorter.getProtocolVersions()
                                      .get(index)
                                      .getVersion();
                viaVersionButton.displayString = ProtocolUtils
                        .getProtocolName(ViaFabric.clientSideVersion);
                info.cancel();
            }
        }
    }

}
