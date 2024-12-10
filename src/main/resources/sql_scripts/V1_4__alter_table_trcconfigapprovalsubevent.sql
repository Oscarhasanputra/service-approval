DROP TABLE IF EXISTS "public"."trc_configapprovalsubevent";
CREATE TABLE "public"."trc_configapprovalsubevent" (
  "id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "code" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "remarks" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "trx_configapproval_id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "type" varchar(255) COLLATE "pg_catalog"."default" NOT NULL
)
;
ALTER TABLE "public"."trc_configapprovalsubevent" OWNER TO "postgres";

-- ----------------------------
-- Checks structure for table trc_configapprovalsubevent
-- ----------------------------
ALTER TABLE "public"."trc_configapprovalsubevent" ADD CONSTRAINT "trc_configapprovalsubevent_type_check" CHECK (type::text = ANY (ARRAY['EVENT'::character varying, 'SUBEVENT'::character varying]::text[]));

-- ----------------------------
-- Primary Key structure for table trc_configapprovalsubevent
-- ----------------------------
ALTER TABLE "public"."trc_configapprovalsubevent" ADD CONSTRAINT "trc_configapprovalsubevent_pkey" PRIMARY KEY ("id");
