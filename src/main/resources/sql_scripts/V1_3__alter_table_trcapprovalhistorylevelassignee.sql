DROP TABLE IF EXISTS "public"."trc_approvalhistorylevelassignee";
CREATE TABLE "public"."trc_approvalhistorylevelassignee" (
  "id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "approval_date" timestamp(6),
  "approval_note" varchar(255) COLLATE "pg_catalog"."default",
  "approval_status" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "assignee_email" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "assignee_id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "assignee_name" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "assignee_telegram" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "assignee_whatsapp" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "send_email_notification" bool NOT NULL,
  "send_telegram_notification" bool NOT NULL,
  "send_wa_notification" bool NOT NULL,
  "trc_approvalhistorylevel_id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL
)
;
ALTER TABLE "public"."trc_approvalhistorylevelassignee" OWNER TO "postgres";

-- ----------------------------
-- Primary Key structure for table trc_approvalhistorylevelassignee
-- ----------------------------
ALTER TABLE "public"."trc_approvalhistorylevelassignee" ADD CONSTRAINT "trc_approvalhistorylevelassignee_pkey" PRIMARY KEY ("id");
