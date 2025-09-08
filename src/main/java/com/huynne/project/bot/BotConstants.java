package com.huynne.project.bot;

import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;

import java.util.ArrayList;
import java.util.List;

public class BotConstants {

    public final static BotCommand INIT = BotCommand.builder()
            .command("init").description("Khởi tạo nhóm gửi nhắc nhở").build();
    public final static BotCommand GET_ID = BotCommand.builder()
            .command("id").description("Lấy Id nhóm gửi nhắc nhở").build();

    public final static BotCommand HELLO_HUYEN = BotCommand.builder()
            .command("hello_huyen").description("Chào Huyền").build();

    public final static BotCommand SCHEDULES = BotCommand.builder()
            .command("schedules").description("Lấy danh sách nhắc nhở").build();

    public final static BotCommand SCHEDULE_CREATE = BotCommand.builder()
            .command("create_schedule").description("Tạo nhắc nhở").build();

    public final static BotCommand SCHEDULE_UPDATE = BotCommand.builder()
            .command("update_schedule").description("Sửa nhắc nhở").build();

    public final static BotCommand SCHEDULE_DELETE = BotCommand.builder()
            .command("delete_schedule").description("Xóa nhắc nhở").build();

    public final static List<BotCommand> COMMAND_LIST = new ArrayList<BotCommand>() {{
        add(HELLO_HUYEN);
        add(INIT);
        add(GET_ID);
        add(SCHEDULES);
        add(SCHEDULE_CREATE);
        add(SCHEDULE_UPDATE);
        add(SCHEDULE_DELETE);
    }};
}
