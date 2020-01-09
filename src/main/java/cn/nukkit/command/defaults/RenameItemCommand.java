package cn.nukkit.command.defaults;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParamType;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.item.Item;
import cn.nukkit.lang.TranslationContainer;
import cn.nukkit.utils.TextFormat;

public class RenameItemCommand extends VanillaCommand {

    public RenameItemCommand(String name) {
        super(name, "%nukkit.command.renameitem.description", "%nukkit.command.renameitem.usage");
        this.setPermission("nukkit.command.renameitem.player");
        this.commandParameters.clear();
        this.commandParameters.put("default",
                new CommandParameter[]{
                        new CommandParameter("customName", CommandParamType.RAWTEXT, false)
                });
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (!this.testPermission(sender)) {
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage(new TranslationContainer("commands.generic.usage", this.usageMessage));

            return false;
        }

        String customName = "";
        for (int i = 0; i < args.length; i++) {
            customName += " " + args[i];
        }
        customName.substring(0, 1);

        if(!(sender instanceof Player)){
            sender.sendMessage(new TranslationContainer(TextFormat.RED + "%commands.generic.ingame"));
        }else {
            Player player = (Player) sender;
            Item item = player.getInventory().getItemInHand();

            if(!item.isNull()){
                item.setCustomName(customName);
                player.getInventory().setItemInHand(item);
                sender.sendMessage(new TranslationContainer("nukkit.command.renameitem.success", customName));
                return true;
            }else {
                sender.sendMessage(new TranslationContainer("commands.generic.usage", this.usageMessage));
            }

        }

        return false;
    }
}
