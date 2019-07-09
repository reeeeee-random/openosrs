import java.util.Iterator;
import java.util.LinkedList;
import net.runelite.mapping.Export;
import net.runelite.mapping.Implements;
import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("ag")
@Implements("WorldMapArea")
public class WorldMapArea {
   @ObfuscatedName("rc")
   @ObfuscatedSignature(
      signature = "Lbh;"
   )
   @Export("friendSystem")
   public static FriendSystem friendSystem;
   @ObfuscatedName("eq")
   @ObfuscatedGetter(
      intValue = -256520591
   )
   @Export("port2")
   static int port2;
   @ObfuscatedName("m")
   @ObfuscatedGetter(
      intValue = 1232579503
   )
   @Export("id")
   int id;
   @ObfuscatedName("f")
   @Export("archiveName")
   String archiveName;
   @ObfuscatedName("q")
   @Export("name")
   String name;
   @ObfuscatedName("w")
   @ObfuscatedGetter(
      intValue = 986239133
   )
   int field1015;
   @ObfuscatedName("o")
   @ObfuscatedGetter(
      intValue = 196514055
   )
   @Export("zoom0")
   int zoom0;
   @ObfuscatedName("u")
   @ObfuscatedSignature(
      signature = "Lhu;"
   )
   @Export("origin0")
   Coord origin0;
   @ObfuscatedName("g")
   @ObfuscatedGetter(
      intValue = 1238532539
   )
   @Export("minX0")
   int minX0;
   @ObfuscatedName("l")
   @ObfuscatedGetter(
      intValue = 225384859
   )
   @Export("maxX0")
   int maxX0;
   @ObfuscatedName("e")
   @ObfuscatedGetter(
      intValue = 963380367
   )
   @Export("minY0")
   int minY0;
   @ObfuscatedName("x")
   @ObfuscatedGetter(
      intValue = 1025105175
   )
   @Export("maxY0")
   int maxY0;
   @ObfuscatedName("d")
   @Export("isMain")
   boolean isMain;
   @ObfuscatedName("k")
   @Export("sections")
   LinkedList sections;

   public WorldMapArea() {
      this.id = -1;
      this.field1015 = -1;
      this.zoom0 = -1;
      this.origin0 = null;
      this.minX0 = Integer.MAX_VALUE;
      this.maxX0 = 0;
      this.minY0 = Integer.MAX_VALUE;
      this.maxY0 = 0;
      this.isMain = false;
   }

   @ObfuscatedName("m")
   @ObfuscatedSignature(
      signature = "(Lgr;II)V",
      garbageValue = "1854512327"
   )
   @Export("read")
   public void read(Buffer var1, int fileId) {
      this.id = fileId;
      this.archiveName = var1.readStringCp1252NullTerminated();
      this.name = var1.readStringCp1252NullTerminated();
      this.origin0 = new Coord(var1.readInt());
      this.field1015 = var1.readInt();
      var1.readUnsignedByte();
      this.isMain = var1.readUnsignedByte() == 1;
      this.zoom0 = var1.readUnsignedByte();
      int var3 = var1.readUnsignedByte();
      this.sections = new LinkedList();

      for (int var4 = 0; var4 < var3; ++var4) {
         this.sections.add(this.readWorldMapSection(var1));
      }

      this.setBounds();
   }

   @ObfuscatedName("f")
   @ObfuscatedSignature(
      signature = "(Lgr;S)Lab;",
      garbageValue = "-13154"
   )
   @Export("readWorldMapSection")
   WorldMapSection readWorldMapSection(Buffer var1) {
      int var2 = var1.readUnsignedByte();
      WorldMapSectionType[] var3 = new WorldMapSectionType[]{WorldMapSectionType.WORLDMAPSECTIONTYPE1, WorldMapSectionType.WORLDMAPSECTIONTYPE3, WorldMapSectionType.WORLDMAPSECTIONTYPE0, WorldMapSectionType.WORLDMAPSECTIONTYPE2};
      WorldMapSectionType var4 = (WorldMapSectionType)ScriptFrame.findEnumerated(var3, var2);
      Object var5 = null;
      switch(var4.type) {
      case 0:
         var5 = new WorldMapSection0();
         break;
      case 1:
         var5 = new WorldMapSection1();
         break;
      case 2:
         var5 = new WorldMapSection2();
         break;
      case 3:
         var5 = new WorldMapSection3();
         break;
      default:
         throw new IllegalStateException("");
      }

      ((WorldMapSection)var5).read(var1);
      return (WorldMapSection)var5;
   }

   @ObfuscatedName("q")
   @ObfuscatedSignature(
      signature = "(IIII)Z",
      garbageValue = "1843012457"
   )
   @Export("containsCoord")
   public boolean containsCoord(int plane, int x, int y) {
      Iterator var4 = this.sections.iterator();

      WorldMapSection var5;
      do {
         if (!var4.hasNext()) {
            return false;
         }

         var5 = (WorldMapSection)var4.next();
      } while(!var5.containsCoord(plane, x, y));

      return true;
   }

   @ObfuscatedName("w")
   @ObfuscatedSignature(
      signature = "(III)Z",
      garbageValue = "-693447297"
   )
   @Export("containsPosition")
   public boolean containsPosition(int x, int y) {
      int var3 = x / 64;
      int var4 = y / 64;
      if (var3 >= this.minX0 && var3 <= this.maxX0) {
         if (var4 >= this.minY0 && var4 <= this.maxY0) {
            Iterator var5 = this.sections.iterator();

            WorldMapSection var6;
            do {
               if (!var5.hasNext()) {
                  return false;
               }

               var6 = (WorldMapSection)var5.next();
            } while(!var6.containsPosition(x, y));

            return true;
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   @ObfuscatedName("o")
   @ObfuscatedSignature(
      signature = "(IIII)[I",
      garbageValue = "745399916"
   )
   @Export("position")
   public int[] position(int plane, int x, int y) {
      Iterator var4 = this.sections.iterator();

      WorldMapSection var5;
      do {
         if (!var4.hasNext()) {
            return null;
         }

         var5 = (WorldMapSection)var4.next();
      } while(!var5.containsCoord(plane, x, y));

      return var5.position(plane, x, y);
   }

   @ObfuscatedName("u")
   @ObfuscatedSignature(
      signature = "(III)Lhu;",
      garbageValue = "1509069978"
   )
   @Export("coord")
   public Coord coord(int x, int y) {
      Iterator var3 = this.sections.iterator();

      WorldMapSection var4;
      do {
         if (!var3.hasNext()) {
            return null;
         }

         var4 = (WorldMapSection)var3.next();
      } while(!var4.containsPosition(x, y));

      return var4.coord(x, y);
   }

   @ObfuscatedName("g")
   @ObfuscatedSignature(
      signature = "(B)V",
      garbageValue = "-17"
   )
   @Export("setBounds")
   void setBounds() {
      Iterator var1 = this.sections.iterator();

      while (var1.hasNext()) {
         WorldMapSection var2 = (WorldMapSection)var1.next();
         var2.expandBounds(this);
      }

   }

   @ObfuscatedName("l")
   @ObfuscatedSignature(
      signature = "(I)I",
      garbageValue = "-628294476"
   )
   @Export("getId")
   public int getId() {
      return this.id;
   }

   @ObfuscatedName("e")
   @ObfuscatedSignature(
      signature = "(B)Z",
      garbageValue = "-39"
   )
   @Export("getIsMain")
   public boolean getIsMain() {
      return this.isMain;
   }

   @ObfuscatedName("x")
   @ObfuscatedSignature(
      signature = "(B)Ljava/lang/String;",
      garbageValue = "-65"
   )
   @Export("getArchiveName")
   public String getArchiveName() {
      return this.archiveName;
   }

   @ObfuscatedName("d")
   @ObfuscatedSignature(
      signature = "(B)Ljava/lang/String;",
      garbageValue = "-66"
   )
   @Export("getName")
   public String getName() {
      return this.name;
   }

   @ObfuscatedName("a")
   @ObfuscatedSignature(
      signature = "(I)I",
      garbageValue = "-1657905623"
   )
   int method386() {
      return this.field1015;
   }

   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "(I)I",
      garbageValue = "-641284417"
   )
   @Export("zoom")
   public int zoom() {
      return this.zoom0;
   }

   @ObfuscatedName("j")
   @ObfuscatedSignature(
      signature = "(B)I",
      garbageValue = "-18"
   )
   @Export("minX")
   public int minX() {
      return this.minX0;
   }

   @ObfuscatedName("s")
   @ObfuscatedSignature(
      signature = "(B)I",
      garbageValue = "0"
   )
   @Export("maxX")
   public int maxX() {
      return this.maxX0;
   }

   @ObfuscatedName("t")
   @ObfuscatedSignature(
      signature = "(I)I",
      garbageValue = "272992390"
   )
   @Export("minY")
   public int minY() {
      return this.minY0;
   }

   @ObfuscatedName("y")
   @ObfuscatedSignature(
      signature = "(I)I",
      garbageValue = "-1621710159"
   )
   @Export("maxY")
   public int maxY() {
      return this.maxY0;
   }

   @ObfuscatedName("h")
   @ObfuscatedSignature(
      signature = "(B)I",
      garbageValue = "5"
   )
   @Export("originX")
   public int originX() {
      return this.origin0.x;
   }

   @ObfuscatedName("b")
   @ObfuscatedSignature(
      signature = "(I)I",
      garbageValue = "-1875921633"
   )
   @Export("originPlane")
   public int originPlane() {
      return this.origin0.plane;
   }

   @ObfuscatedName("c")
   @ObfuscatedSignature(
      signature = "(I)I",
      garbageValue = "-135283879"
   )
   @Export("originY")
   public int originY() {
      return this.origin0.y;
   }

   @ObfuscatedName("p")
   @ObfuscatedSignature(
      signature = "(I)Lhu;",
      garbageValue = "-1411761003"
   )
   @Export("origin")
   public Coord origin() {
      return new Coord(this.origin0);
   }

   @ObfuscatedName("m")
   @ObfuscatedSignature(
      signature = "(III)I",
      garbageValue = "-1490951132"
   )
   static int method427(int var0, int var1) {
      if (var0 == -2) {
         return 12345678;
      } else if (var0 == -1) {
         if (var1 < 0) {
            var1 = 0;
         } else if (var1 > 127) {
            var1 = 127;
         }

         var1 = 127 - var1;
         return var1;
      } else {
         var1 = (var0 & 127) * var1 / 128;
         if (var1 < 2) {
            var1 = 2;
         } else if (var1 > 126) {
            var1 = 126;
         }

         return (var0 & 65408) + var1;
      }
   }

   @ObfuscatedName("kc")
   @ObfuscatedSignature(
      signature = "(B)V",
      garbageValue = "-26"
   )
   static void method428() {
      if (Client.oculusOrbState == 1) {
         Client.field199 = true;
      }

   }
}
