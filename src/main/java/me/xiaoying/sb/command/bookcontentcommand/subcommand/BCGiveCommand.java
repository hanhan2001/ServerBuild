package me.xiaoying.sb.command.bookcontentcommand.subcommand;

import me.xiaoying.sb.command.Command;
import me.xiaoying.sb.command.SubCommand;
import me.xiaoying.sb.constant.BookContentConstant;
import me.xiaoying.sb.entity.BookContentEntity;
import me.xiaoying.sb.factory.VariableFactory;
import me.xiaoying.sb.service.BookContentServer;
import me.xiaoying.sb.utils.ServerUtil;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.BookMeta;

import java.util.ArrayList;
import java.util.List;

@Command(values = "give", length = 1)
public class BCGiveCommand extends SubCommand {
    @Override
    public void registerCommand(SubCommand command) {

    }

    @Override
    public boolean performCommand(CommandSender sender, String[] args) {
        if (!ServerUtil.hasPermission(sender, "sb.bc.give", "sb.bc.admin", "sb.admin") && !sender.isOp()) {
            sender.sendMessage(new VariableFactory(BookContentConstant.MESSAGE_NOPERMISSION)
                    .prefix(BookContentConstant.MESSAGE_PREFIX)
                    .date(BookContentConstant.SET_VARIABLE_DATEFORMAT)
                    .color()
                    .getString());
            return false;
        }

        if (!(sender instanceof Player)) {
            sender.sendMessage(new VariableFactory(BookContentConstant.MESSAGE_USEPLAYER)
                            .prefix(BookContentConstant.MESSAGE_PREFIX)
                            .date(BookContentConstant.SET_VARIABLE_DATEFORMAT)
                            .color()
                            .getString());
            return false;
        }

        Player player = (Player) sender;
        String id = args[0];
        BookContentEntity bookContentEntity = null;
        for (BookContentEntity bookContent : BookContentServer.getBookContents()) {
            if (!bookContent.getId().equalsIgnoreCase(id))
                continue;
            bookContentEntity = bookContent;
            break;
        }

        if (bookContentEntity == null) {
            sender.sendMessage(new VariableFactory(BookContentConstant.MESSAGE_NOTFOUNDBOOK)
                            .prefix(BookContentConstant.MESSAGE_PREFIX)
                            .date(BookContentConstant.SET_VARIABLE_DATEFORMAT)
                            .player(player)
                            .placeholder(player)
                            .color()
                            .getString());
            return false;
        }

        ItemStack itemStack = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta bookMeta = (BookMeta) itemStack.getItemMeta();
        assert (bookMeta != null);
        bookMeta.setDisplayName(new VariableFactory(bookContentEntity.getName())
                        .prefix(BookContentConstant.MESSAGE_PREFIX)
                        .date(BookContentConstant.SET_VARIABLE_DATEFORMAT)
                        .player(player)
                        .placeholder(player)
                        .color()
                        .getString());
        bookMeta.setPages(new String[]{bookContentEntity.getContent()});
        itemStack.setItemMeta(bookMeta);
        PlayerInventory playerInventory = ((Player) sender).getInventory();
        for (int i = 0; i < playerInventory.getSize(); ++i) {
            if (playerInventory.getItem(i) != null) continue;
            ((Player) sender).getInventory().addItem(new ItemStack[]{itemStack});
            break;
        }

        player.sendMessage(new VariableFactory(BookContentConstant.MESSAGE_GIVEBOOK)
                        .prefix(BookContentConstant.MESSAGE_PREFIX)
                        .name(bookContentEntity.getName())
                        .date(BookContentConstant.SET_VARIABLE_DATEFORMAT)
                        .player(player)
                        .placeholder(player)
                        .color()
                        .getString());
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, org.bukkit.command.Command command, String s, String[] args) {
        List<String> list = new ArrayList<>();
        for (BookContentEntity bookContent : BookContentServer.getBookContents())
            list.add(bookContent.getId());
        return list;
    }
}