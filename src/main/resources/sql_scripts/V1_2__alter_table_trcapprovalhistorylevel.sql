DROP TABLE IF EXISTS "public"."trc_approvalhistorylevel";
CREATE TABLE "public"."trc_approvalhistorylevel" (
  "id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "approval_date" timestamp(6),
  "approval_note" varchar(255) COLLATE "pg_catalog"."default",
  "approval_status" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "level" int4 NOT NULL,
  "send_notification_date" timestamp(6),
  "total_approval_needed" int4 NOT NULL,
  "trx_approvalhistory_id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL
)
;
ALTER TABLE "public"."trc_approvalhistorylevel" OWNER TO "postgres";

-- ----------------------------
-- Primary Key structure for table trc_approvalhistorylevel
-- ----------------------------
ALTER TABLE "public"."trc_approvalhistorylevel" ADD CONSTRAINT "trc_approvalhistorylevel_pkey" PRIMARY KEY ("id");
