package gg.steve.mc.frodo.eggs.framework.cmd;

import gg.steve.mc.frodo.eggs.cmd.subs.GiveSubCmd;
import gg.steve.mc.frodo.eggs.cmd.subs.InfoSubCmd;
import gg.steve.mc.frodo.eggs.cmd.subs.ListSubCmd;
import gg.steve.mc.frodo.eggs.cmd.subs.TpSubCmd;
import gg.steve.mc.frodo.eggs.framework.cmd.misc.HelpSubCmd;
import gg.steve.mc.frodo.eggs.framework.cmd.misc.ReloadSubCmd;

public enum SubCommandType {
    HELP_CMD(new HelpSubCmd()),
    LIST_CMD(new ListSubCmd()),
    GIVE_CMD(new GiveSubCmd()),
    INFO_CMD(new InfoSubCmd()),
    TP_CMD(new TpSubCmd()),
    RELOAD_CMD(new ReloadSubCmd());

    private SubCommand sub;

    SubCommandType(SubCommand sub) {
        this.sub = sub;
    }

    public SubCommand getSub() {
        return sub;
    }
}
