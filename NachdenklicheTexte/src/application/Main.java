package application;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import javax.imageio.ImageIO;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/*
 * by GerH, 2017
 */
public class Main extends Application {

	@FXML
	private TextArea TextFeld;
	@FXML
	private Button UebersetzButton;
	@FXML
	private SplitMenuButton probSplitMenu;
	@FXML
	private ImageView BackgroundImgView;
	@FXML
	private Label textLabel;
	@FXML
	private Pane picPane;
	@FXML
	private CheckBox translateBox;

	public int probValue = 30;
	
	int spruecheInitialSize = 0;
	ArrayList<String> sprueche = new ArrayList<String>( 
            Arrays.asList(
			"Ärgere dich nicht, wenn dir ein Vogel auf den Kopf kackt, sondern freu dich, dass Elefanten nicht fliegen können. ",
			"Auch aus Steinen, die einem in den Weg gelegt werden, kann man Schönes bauen. \n Johann Wolfgang von Goethe (1749-1832)",
			"Auch Wolkenkratzer haben mal als Keller angefangen. ",
			"Das Übel, das uns trifft, ist selten oder nie so schlimm, als das, welches wir befürchten.\nFriedrich Schiller (1759-1805)",
			"Das Urteil der Menge mache dich immer nachdenkend, doch niemals verzagt.\nAugust von Platen (1796-1835)",
			"Dein Auge kann die Welt trüb oder hell dir machen: Wie du sie ansiehst, wird sie weinen oder lachen.\nFriedrich Rückert (1788-1866)",
			"Die einzige Begrenzung, das Morgen zu verwirklichen, werden unsere Zweifel von heute sein.\nFranklin D. Roosevelt (1882-1945)",
			"Drei Dinge helfen, die Mühen des Lebens zu tragen: Die Hoffnung, der Schlaf und das Lachen.\nImmanuel Kant (1724-1804)",
			"Eine Kleinigkeit tröstet uns, weil eine Kleinigkeit uns betrübt.\nBlaise Pascal (1623-1662)",
			"Ein Optimist ist ein Mensch, der ein Dutzend Austern bestellt, in der Hoffnung, sie mit der Perle, die er darin findet, bezahlen zu können.\nTheodor Fontane (1819-1898)",
			"Es hat immer in meiner Natur gelegen, mir zuzurufen: Vorwärts, selbst wenn vorwärts Niederlage bedeutete.\nRomain Rolland (1866-1944)",
			"Es kommt oft anders. Das ist ein wahres Wort und zugleich auch ein guter Trost, der dem Menschen in seinem Erdenleben auf den Weg gegeben worden ist.\nWilhelm Raabe (1831-1910)",
			"Genieße das Leben ständig, denn du bist länger tot als lebendig!\nSprichwort",
			"Heiterkeit des Herzens schließt wie der Frühling alle Blüten des Inneren auf.\nJean Paul (1763-1825)",
			"Heiterkeit ist der Ursprung aller glücklichen Einfälle.\nLuc de Clapiers, Marquis de Vauvenargues (1715-1747)",
			"Jeder bedarf allezeit eines gewissen Quantums an Sorge oder Schmerz oder Not, wie das Schiff des Ballastes, um fest und gerade zu stehen.\nArthur Schopenhauer (1788-1860)",
			"Lass nur die Sorge sein, das gibt sich alles schon, und fällt der Himmel ein, kommt doch eine Lerche davon.\nJohann Wolfgang von Goethe (1749-1832)",
			"Lebe so, dass deine Geschichte Zukunft hat.\n",
			"Dem Heiteren erscheint die Welt auch heiter. \n(Johann Wolfgang von Goethe 1749-1832)",
			"Mag auch das Böse sich noch so sehr vervielfachen, niemals vermag es das Gute ganz aufzuzehren.\nThomas von Aquin (1225-1274)",
			"Man muss sein Leben aus dem Holze schnitzen, das man hat, und wenn es krumm und knorrig wäre.\nTheodor Storm (1817-1888)",
			"Man muss versuchen, sich über nichts zu betrüben, und alles was geschieht, als das Beste anzusehen.\nBlaise Pascal (1623-1662)",
			"Nenne dich nicht arm, weil deine Träume nicht in Erfüllung gegangen sind, wirklich arm ist nur, wer nie geträumt hat!\nMarie von Ebner-Eschenbach (1830-1916)",
			"Nur das fröhliche Herz allein ist fähig, Wohlgefallen am Guten zu empfinden.\nImmanuel Kant (1724-1804)",
			"Pessimisten stehen im Regen, Optimisten duschen unter den Wolken.\nUnbekannt",
			"Selbst wenn ich wüsste, dass morgen die Welt unterginge, würde ich heute noch ein Apfelbäumchen pflanzen.\nMartin Luther (1483-1546)",
			"Wahrheitsliebe zeigt sich darin, dass man überall das Gute zu schätzen weiß.\nJohann Wolfgang von Goethe (1749-1832)",
			"Wenn du am Morgen erwachst, denke daran, was für ein Schatz es ist zu leben, zu atmen und sich freuen zu können.\nMark Aurel (121-180)",
			"Wenn einem das Wasser bis zum Halse steht, sollte man nicht auch noch den Kopf hängen lassen.",
			"Wenn ihr euch nur selbst vertraut, vertrauen euch die andren Seelen.\nJohann Wolfgang von Goethe (1749-1832)",
			"Zuviel Vertrauen ist häufig eine Dummheit, zuviel Misstrauen ist immer ein Unglück.\nJohann Nestroy (1801-1862)",
			"Alle Gelegenheiten, glücklich zu werden, helfen nicht, wenn man nicht den Verstand hat, sie zu nutzen.\nJohann Peter Hebel (1760-1826)",
			"Alles, was die Seele durcheinanderrüttelt, ist Glück.\nArthur Schnitzler (1862-1931)",
			"Das Bewusstsein eines wohlverbrachten Lebens und die Erinnerung vieler guter Taten sind das größte Glück auf Erden!\nCicero (106-43 v.Chr.)",
			"Das ganze Glück des Menschen besteht darin, bei anderen Achtung zu genießen.\nBlaise Pascal (1623-1662)",
			"Das Glück, das dir am meisten schmeichelt, betrügt dich am ehesten.\nFranz Kafka (1883-1924)",
			"Das Glück des Menschen beruht darauf, dass es für ihn undiskutierbare Wahrheiten gibt.\nFriedrich Nietzsche (1844-1900)",
			"Das Glück gleicht oft den reichen, verschwenderischen Frauen, welche die Häuser ruinieren, denen sie eine große Mitgift zugebracht haben.\nNicolas-Sebastien Chamfort (1741-1794)",
			"Das Glück ist keine leichte Sache: Es ist sehr schwer, es in uns, und unmöglich, es anderswo zu finden.\nNicolas-Sebastien Chamfort (1741-1794)",
			"Das höchste Glück des Lebens besteht in der Überzeugung, geliebt zu sein.\nVictor Hugo (1802-1885)",
			"Dass andere Leute kein Glück haben, finden wir sehr leicht natürlich, dass wir selbst keines haben, immer unfassbar.\nMarie von Ebner-Eschenbach (1830-1916)",
			"Der Augenblick nur entscheidet über das Leben des Menschen und über sein ganzes Glück.\nJohann Wolfgang von Goethe (1749-1832)",
			"Die Fähigkeit, glücklich zu leben, kommt aus einer Kraft, die der Seele innewohnt.\nMarcus Aurelius (121-180)",
			"Die Glücklichen sind reich, nicht Reiche glücklich.\nFriedrich Halm (1908-1871)",
			"Die höchste Form des Glücks ist ein Leben mit einem gewissen Grad an Verrücktheit.\nErasmus von Rotterdam (1466-1536)",
			"Fleiß ist die Mutter des Glücks, und den Fleißigen schenkt Gott alle Dinge.\nBenjamin Franklin (1706-1790)",
			"Glücke kennt man nicht, drinne man geboren; Glücke kennt man erst, wenn man es verloren.\nFriedrich von Logau (1605-1655)",
			"Glück ist das einzige, was wir anderen geben können, ohne es selbst zu haben.\nCarmen Sylva (1843-1916)",
			"Glück kann viele Formen haben, wodurch es sich nicht immer gleich zu erkennen gibt.\nKlemens Oberforster",
			"Glücklich allein ist die Seele, die liebt. (mehr zu Liebe)\nJohann Wolfgang von Goethe (1749-1832)",
			"Glücklich ist, wer fern den Geschäften.\nHoraz (um 30 v.Chr. Geburt)",
			"Glücklich machen ist das höchste Glück. Aber auch dankbar annehmen ist ein Glück.\nTheodor Fontane (1819-1898)",
			"Glücklicher als der Glücklichste ist, wer andere Menschen glücklich machen kann.\nAlexandre Dumas (1802-1870)",
			"Glücklich ist nicht, wer anderen so vorkommt, sondern wer sich selbst dafür hält.\nSeneca (um Christi Geburt)",
			"Glück und Unglück sind Namen für Dinge, deren äußerste Grenzen wir nicht kennen.\nJohn Locke (1632-1704)",
			"Glück ist, wenn das Pech die anderen trifft.\nHoraz (65-8 v.Chr.)",
			"Höhepunkt des Glücks ist es, wenn der Mensch bereit ist, das zu sein, was er ist.\nErasmus von Rotterdam (1466-1536)",
			"Jedes Wetter tobt sich aus. \nEines Tages haben wir wieder den Regenbogen und das Fest der Versöhnung.\nTheodor Fontane (1819-1898)",
			"Man muss sein Glück teilen, um es zu multiplizieren.\nMarie von Ebner-Eschenbach (1830-1916)",
			"Mehr als Frieden in sich kann auch der Größte nicht haben.\nOtto von Leixner (1847-1907)",
			"Nenne einen Menschen nicht glücklich vor seinem Ende, sondern sage nur, es ginge ihm wohl.\nHerodot (um 450 v.Chr.)",
			"Nicht die Dinge selbst, sondern nur unsere Vorstellungen darüber, machen uns glücklich oder unglücklich.\nEpiktet (um 100 n.Chr.)",
			"Schön ist es miteinander zu schweigen, schöner miteinander zu lachen.\nFriedrich Nietzsche (1844-1900)",
			"Trotz aller unserer Wanderungen ist das Glück nur in einem engen Kreise und mitten unter Gegenständen zu finden, welche in unserem unmittelbaren Bereich liegen.\nEdward George Bulwer (1803-1873)",
			"Um glücklich zu sein, muss man seine Vorurteile abgelegt und seine Illusion behalten haben.\nMarquise de Chatelet (1706-1749)",
			"Vergnügen kann auf der Illusion beruhen, doch das Glück beruht allein auf der Wahrheit.\nNicolas-Sebastien Chamfort (1741-1794)",
			"Wem Fortuna ein Haus schenkt, dem schenkt sie auch Möbel.\nWilhelm Busch (1832-1908)",
			"Wenn du einen Menschen glücklich machen willst, dann füge nichts seinem Reichtum hinzu, sondern nimm ihm einige von seinen Wünschen.\nEpikur von Samos (um 300 v.Chr.)",
			"Wenn man glücklich ist, sollte man nicht noch glücklicher sein wollen.\nTheodor Fontane (1819-1889)",
			"Wer ständig glücklich sein möchte, muss sich oft verändern.\nKonfuzius (um 500 v.Chr.)",
			"Wie wenig gehört zum Glücke! Der Ton eines Dudelsacks. Ohne Musik wäre das Leben ein Irrtum.\nFriedrich Nietzsche (1844-1900)",
			"Zwei Dinge sind schädlich für jeden, der die Stufen des Glücks will ersteigen:\nSchweigen, wenn es Zeit ist zu reden, und reden, wenn es Zeit ist zu schweigen.\nFriedrich von Bodenstedt (1819-1892)",
			"Richtet nicht und ihr werdet nicht gerichtet werden.\nBibel",
			"Aber der Gerechte wird seinen Weg behalten; und wer reine Hände hat, wird an Stärke zunehmen.\nBibel (Hiob 17, 9)",
			"Das Gesetz hat die Menschen niemals gerechter gemacht; im Gegenteil, infolge der Achtung vor dem Gesetz werden gute Menschen zu Vollziehern der Ungerechtigkeit.\nHenry David Thoreau (1817-1862)",
			"Das harte Wort schmerzt immer, sei es auch ganz gerecht.\nSophokles (um 450 v.Chr.)",
			"Die Gerechtigkeit ist unsterblich.\naus dem Buch der Weisheit 1, 15 (um 50 v.Chr.)",
			"Der Eckstein der Gerechtigkeit ist die Gleichheit vor dem Gesetz.\nRobert Bosch (1861-1942)",
			"Die Bürden, welche alle Menschen tragen, sind nicht so verschieden, als sie scheinen, ihre Schwere oder ihre Leichtigkeit hängt nicht vom eigenen Gewicht ab, sondern von der Gewohnheit und dem Gemüte, welches sie trägt.\nJeremias Gotthelf (1797-1854)",
			"Es wird die Zeit kommen, da das Verbrechen am Tier ebenso geahndet wird, wie das Verbrechen am Menschen.\nLeonardo da Vinci (1452-1519)",
			"Für Freunde zürnend sich ereifern ist kein Unrecht\nEuripides (um 450 v.Chr.)",
			"Das Recht auf Dummheit gehört zur Garantie der freien Entfaltung der Persönlichkeit.\nMark Twain (1835-1910)",
			"Den Gebrauch der Kräfte, die man hat, ist man denen schuldig, die sie nicht haben.\nCarl Schurz (1829-1906)",
			"Der Gütige ist frei, auch wenn er ein Sklave ist. Der Böse ist ein Sklave, auch wenn er ein König ist.\nAugustinus Aurelius (354-430)",
			"Die Gerechtigkeit ist nichts anderes als die Nächstenliebe des Weisen.\nGottfried Wilhelm von Leibniz (1646-1716)",
			"Die Gesetze gleichen den Spinnweben: Kleine werden gefangen, Große zerreißen sie.\nSolon (um 600 v.Chr.)",
			"Die schlimmste Art der Ungerechtigkeit ist die vorgespielte Gerechtigkeit.\nPlaton (um 400 v.Chr.)",
			"Die wahre Freiheit ist nichts anderes als Gerechtigkeit.\nJohann Gottfried Seume (1763-1810)",
			"Eigentlich ist es nur des Menschen, gerecht zu sein und Gerechtigkeit zu üben,\ndenn die Götter lassen alle gewähren: Ihre Sonne scheint über Gerechte und Ungerechte.\nJohann Wolfgang von Goethe (1749-1832)",
			"Einer, dessen Herz von Eifersucht verzehrt wird, ist niemals gerecht.\nGeorge Sand (1804-1876)",
			"Es gibt Dinge, die nicht bestraft werden und dem Menschen doch das Kostbarste stehlen: die Zeit.\nNapoleon Bonaparte (1769-1821)",
			"Es ist gleich, ob du Geld hast oder nicht.\nWenn du kein Geld hast, hast du unrecht.\nChinesisches Sprichwort",
			"Es kann keiner gerecht sein, der nicht menschlich ist.\nLuc de Clapiers, Marquis de Vauvenargues (1715-1747)",
			"Es schadet einem nicht, wenn einem Unrecht geschieht. Man muss es nur vergessen können.\nKonfuzius (um 500 v.Chr.)",
			"Gerechtigkeit ohne Stärke ist Ohnmacht, Stärke ohne Gerechtigkeit ist Tyrannei.\nBlaise Pascal (1623-1662)",
			"Gott will uns nicht durch eigene, sondern durch fremde Gerechtigkeit und\nWeisheit selig machen, durch eine Gerechtigkeit, die nicht aus uns kommt\nund aus uns wächst, sondern von anderswoher zu uns kommt.\nMartin Luther (1483-1546)",
			"Klage nicht, dass dir im Leben ward vereitelt manches Hoffen, hat, was du gefürchtet eben, doch auch meist dich nicht betroffen.\nFriedrich Rückert (1788-1866)",
			"Gutes wird mit Gutem, Böses mit Bösem vergolten.\nChinesische Weisheit",
			"Lass dein Leben zum Reibungswiderstand gegen Ungerechtigkeit werden!\nHenry David Thoreau (1817-1862)",
			"Man empfindet es oft als ungerecht, dass Menschen, die Stroh im Kopf haben, auch noch Geld wie Heu besitzen.\nGerhard Uhlenbruck (geb. 1929)",
			"Man sollte nicht den Respekt vor dem Gesetz pflegen, sondern vor der Gerechtigkeit.\nHenry David Thoreau (1817-1862)",
			"Nichts auf der Welt ist so gerecht verteilt wie der Verstand.\nDenn jedermann ist überzeugt, dass er genug davon habe.\nRene Descartes (1596-1650)",
			"Nur die Lüge braucht die Stütze der Staatsgewalt, die Wahrheit steht von alleine aufrecht.\nBenjamin Franklin (1706-1790)",
			"Schwer ist es, die rechte Mitte zu treffen:\nDas Herz zu härten für das Leben, es weich zu halten für das Lieben.\nJeremias Gotthelf (1797-1854)",
			"Strafe ist Gerechtigkeit für die Ungerechten.\nAugustinus von Hippo (354-430)",
			"Um anderen gegenüber gerecht sein zu können, muss man sich selbst gegenüber ehrlich sein\nJean Baptiste Rousseau (1671-1741)",
			"Vergib stets deinen Feinden. Nichts ärgert sie so.\nOscar Wilde (1854-1900)",
			"Wenn die Gerechtigkeit untergeht, hat es keinen Wert mehr, dass Menschen auf Erden leben.\nImmanuel Kant (1724-1804)",
			"Wenn du die Einsamkeit fürchtest, versuche nicht gerecht zu sein.\nJules Renard (1864-1910)",
			"Wer der Gerechtigkeit folgen will durch dick und dünn, muss lange Stiefel haben.\nWilhelm Busch (1832-1908)",
			"Wer Recht erkennen will, muss zuvor in richtiger Weise gezweifelt haben.\nAristoteles (384-322 v.Chr.)",
			"Wie groß die Schar der Bewunderer ist, so groß ist die Schar der Neider.\nSeneca (lebte um Christi Geburt)",
			"Wo alle verurteilen, muss man prüfen und wo alle loben, auch.\nKonfuzius (um 500 v.Chr.)",
			"90% aller Banken, Medien, Konzerne, Gold-, Aktien-, Devisen- Grund- und Immobilienwerte befinden sich im Besitz von nur 5% der Menschheit.\nDiese Tatsache definiert die Qualität der Begriffe Demokratie, Gleichheit, Wahrheit und Gerechtigkeit.\nThomas Pfitzer (geb. 1961)",
			"Welch ein Anker ist die Hoffnung!\nSully Prudhomme (1839-1907)",
			"Alle wahre Frömmigkeit hat Kraft.\nJohann Heinrich Pestalozzi (1746-1827)",
			"Da die Zeit kurz ist, begrenze deine lange Hoffnung!\nHoraz (65-8 v.Chr.)",
			"Das bloß harrende Hoffen ist nur das Hoffen der Toren. Man muss kämpfen, um zu hoffen, wie man hoffen muss, um zu ertragen.\nFriedrich Schleiermacher (1768-1834)",
			"Denn überall, wo größere Hoffnungen sind, da findet auch heftigerer Neid statt, gefährlicherer Hass und heimtückischere Eifersucht.\nLukian von Samosata (um 150 n.Chr.)",
			"Die Hoffnung ist der Regenbogen über dem herabstörzenden Bach des Lebens.\nFriedrich Nietzsche (1844-1900)",
			"Die Hoffnung, so trügerisch sie ist, dient wenigstens dazu, uns auf angenehmem Weg an das Ende des Lebens zu führen.\nFrancois de La Rochefoucauld (1613-1680)",
			"Eines Tages wird alles gut sein, das ist unsere Hoffnung. Heute ist alles in Ordnung, das ist unsere Illusion.\nVoltaire (1694-1778)",
			"Ein Schiff sollte man nicht an einen einzigen Anker und das Leben nicht an eine einzige Hoffnung binden.\nEpiktet (um 100 n.Chr.)",
			"Hoffe nicht ohne Zweifel, zweifle nicht ohne Hoffnung.\nSeneca (lebte um Christi Geburt)",
			"Hoffen und Harren macht manchen zum Narren.\nOvid (um Christi Geburt)",
			"Hoffnung ist die zweite Seele der Unglücklichen.\nJohann Wolfgang von Goethe (1749-1832)",
			"Hoffnung ist ein gutes Frühstück, aber ein schlechtes Abendbrot.\nFrancis Bacon (1561-1626)",
			"Hoffnung und Freude sind die besten Ärzte.\nWilhelm Raabe (1831-1910)",
			"Wo Leben ist, da darf auch Hoffnung sein.\nHenrik Ibsen (1828-1906)",
			"Irgendeine Hoffnung muss der Mensch haben, soll er nicht verdorren und verwelken.\nPaul Busson (1873-1924)",
			"Jede Hoffnung ist eigentlich eine gute Tat.\nJohann Wolfgang von Goethe (1749-1832)",
			"Jeder Wunsch hienieden, das ist mal so, wird begleitet von einem neckischen 'vielleicht'.\nWilhelm Busch (1832-1908)",
			"Mut und Liebe haben eines gemeinsam: Beide werden von der Hoffnung genährt.\nNapoleon Bonaparte (1769-1821)",
			"Nur durch die Hoffnung bleibt alles bereit, immer wieder neu zu beginnen.\nCharles Peguy (1873-1914)",
			"Raube dem Pilger die Hoffnung an sein Ziel zu gelangen, und die Kräfte des Wanderers brechen zusammen.\nWilhelm von Saint-Thierry (um 1100)",
			"Was wäre das Leben ohne Hoffnung? Ein Funke, der aus der Kohle springt und verlischt.\nFriedrich Hölderlin (1770-1843)",
			"Wer hofft, hat schon gesiegt und siegt weiter.\nJean Paul (1763-1825)",
			"Dass es unzählige Krankheiten gibt, ist nicht verwunderlich; man braucht nur die Köche zu zählen.\nSeneca (um Christi Geburt)",
			"Die größte aller Torheiten ist, seine Gesundheit aufzuopfern, für was es auch sei.\nArthur Schopenhauer (1788-1860)",
			"Die Krankheiten des Gedankens sind zerstörender und häufiger als die Krankheiten des Körpers.\nöstliche Weisheit",
			"Eine der verbreitesten Krankheiten ist die Diagnose.\nKarl Kraus (1874-1936)",
			"Gesundheit ist ein Zustand des völligen körperlichen, geistigen und sozialen Wohlbefindens und nicht nur die Abwesenheit von Krankheit.\nDefinition von 'Gesundheit' der Weltgesundheitsorganisation (WHO)",
			"Man sollte niemals zu einem Arzt gehen, ohne zu wissen, was dessen Lieblingsdiagnose ist.\nHenry Fielding (1707-1754)",
			"Seien Sie vorsichtig beim Lesen von Gesundheitsbüchern: Ein Druckfehler kann Ihr Tod sein.\nMark Twain (1835-1910)",
			"Unentschlossenheit ist die größte Krankheit.\nJohann Wolfgang von Goethe (1749-1832)\nWeisheiten zu Krankheit",
			"Das Gefühl der Gesundheit erwirbt man durch Krankheit.\nGeorg Christoph Lichtenberg (1742-1799)",
			"Die Kranken geben bei weitem nicht so viel Geld aus, um gesund, als die Gesunden, um krank zu werden.\nJohann Nepomuk Nestroy (1801-1862)",
			"Es gibt 1000 Krankheiten, aber nur eine Gesundheit.\nArthur Schopenhauer (1788-1860)",
			"In dem Augenblick, in dem ein Mensch den Sinn und den Wert des Lebens bezweifelt, ist er krank.\nSigmund Freud (1856-1939)",
			"In der einen Hälfte des Lebens opfern wir unsere Gesundheit, um Geld zu erwerben. In der anderen Hälfte opfern wir Geld, um die Gesundheit wiederzuerlangen.\nVoltaire (1694-1778)",
			"Krankheiten befallen uns nicht aus heiterem Himmel, sondern entwickeln sich aus täglichen Sünden wider die Natur. Wenn sich diese gehäuft haben, brechen sie unversehens hervor.\nHippokrates (um 400 v.Chr.)",
			"Krankheit lässt den Wert der Gesundheit erkennen.\nHeraklit (um 500 v.Chr.)",
			"Liebe ist von allen Krankheiten noch die gesündeste.\nEuripides (um 450 v.Chr.)",
			"Wer nicht jeden Tag etwas für seine Gesundheit aufbringt, muss eines Tages sehr viel Zeit für die Krankheit opfern.\nSebastian Anton Kneipp (1821-1897)",
			"Willst du gesund werden, so zieh in ein Holzhaus.\nindische Weisheit",
			"Alle Lebewesen außer den Menschen wissen, dass der Hauptzweck des Lebens darin besteht, es zu genießen.\nSamuel Butler (1835-1902)",
			"Bevor wir recht wissen, was Leben heißt, ist es schon halb vorbei.\nLudwig Börne (1786-1837)",
			"Die Kunst des Lebens besteht darin, das Gute und das Böse in uns in Einklang zu bringen, und den Einklang zu genießen.\nThomas Pfitzer (geb. 1961)",
			"Die Lebensspanne ist die selbe, ob man sie lachend oder weinend verbringt.\nKonfuzius (um 500 v.Chr.)",
			"Es ist wichtiger, dass jemand sich über eine Rosenblüte freut, als dass er ihre Wurzel unter das Mikroskop bringt.\nOscar Wilde (1854-1900)",
			"Frage nicht, was das Geschick morgen will beschließen, unser ist der Augenblick, lass uns den genießen.\nFriedrich Rückert (1788-1866)",
			"Mögest du alle Tage deines Lebens leben.\nJonathan Swift (1667-1745)",
			"Genau genommen leben sehr wenig Menschen in der Gegenwart. Die meisten bereiten sich vor, demnächst zu leben.\nJonathan Swift (1667-1745)",
			"Genieße das Leben ständig, denn du bist länger tot als lebendig!",
			"Genieße jeden Augenblick, denn du weißt nie, wann der letzte gekommen ist.",
			"Man muss alt geworden sein, also lange gelebt haben, um zu erkennen, wie kurz das Leben ist.\nArthur Schopenhauer (1788-1860)",
			"Schildkröten können über den Weg mehr erzählen als Hasen.\nSprichwort",
			"Träume nicht dein Leben, sondern lebe deine Träume.\nSprichwort",
			"Beende jeden Tag und sei fertig mit ihm. Morgen ist ein neuer Tag. Beginne ihn gut.\nRalph Waldo Emerson (1803-1882)",
			"Das Gestern ist fort, das Morgen nicht da. Leb' also heute!\nPythagoras von Samos (um 550 v.Chr.)",
			"Der heutige Tag ist des gestrigen Schüler.\nPublilius Syrus (um 50 v.Chr.)",
			"Ein Heute ist besser als zehn Morgen.\nSprichwort",
			"Europa: Samsung Galaxy Sx oder iPhone Ys?\nAfrika: Brot oder Wasser?",
			"Heute ist die 'gute alte Zeit',\nder wir in 10 Jahren nachtrauern werden.",
			"In dem Heute wandelt schon das Morgen.\nFriedrich Schiller (1759-1805)",
			"In unserer Kultur wird nach Stundensatz abgerechnet, nicht nach Qualität. Deshalb fallen einigen Leuten nach 2 Jahren die Fliesen von der Wand.\nThomas Pfitzer (geb. 1961)",
			"Nicht Genies, nicht raffinierte Techniker, nicht Menschenverächter, sondern schlichte, einfache, gerade Menschen werden wir brauchen.\nDietrich Bonhoeffer (1906-1945)",
			"Was wäre der Mensch ohne Telefon? Ein armes Luder. Was ist er aber mit Telefon? Ein armes Luder.\nKurt Tucholsky (1890-1935)",
			"Wenn der letzte Baum gefällt ist, wenn der letzte Fluss vergiftet wurde,\nwenn der letzte Fisch gefangen ist, dann werden wir feststellen,\ndass wir Genforscher essen können.\nThomas Pfitzer (geb. 1961)",
			"Am Anfang steht der Glaube, am Ziel die Schau.\nAugustinus von Hippo (354-430)",
			"Das Beste steht nicht immer in den Büchern, sondern in der Natur.\nAdalbert Stifter (1805-1868)",
			"Das ganze Leben ist ein Versuch. Je mehr Versuche du durchführst, desto besser.\nRalph Waldo Emerson (1803-1882)",
			"Denn, was man wünscht, wenn man's hat, so ist man darum doch nicht satt.\nMatthias Claudius (1740-1815)",
			"Der Kluge horcht nach der Vergangenheit, handelt nach der Gegenwart und denkt an die Zukunft.\nitalienisches Sprichwort",
			"Tadle nicht den Fluss, wenn du ins Wasser fällst.\nChinesische Weisheit",
			"Die Dinge haben nur den Wert, den man ihnen verleiht.\nJean Baptiste Moliere (1622-1673)",
			"Die Langeweile hat kein Gesicht.\nPaul Valery (1871-1945)",
			"Für jede angenehme Erwartung gibt's mindestens drei unangenehme Möglichkeiten.\nWilhelm Busch (1832-1908)",
			"Glaubt mir: Der Herrgott hat in seinen Naturgesetzen gut gesorgt, wenn der Mensch nur danach lebt!\nSebastian Kneipp (1821-1897)",
			"In der Not frisst der Teufel Fliegen.\nSprichwort",
			"Sei deines Willens Herr und deines Gewissens Knecht.\nMarie von Ebner-Eschenbach (1830-1916)",
			"Wer mit dir über andere lästert, lästert auch mit anderen über dich.",
			"Das Leben meistert man lächelnd oder überhaupt nicht.\nChinesisches Sprichwort",
			"Der Sache ergeben sein, nicht den Menschen!\nJohann Gottlieb Fichte (1762-1814)",
			"Die größte Offenbarung ist die Stille.\nLaotse (um 400 v.Chr.)",
			"Die Wahrheit richtet sich nicht nach uns, wir müssen uns nach ihr richten.\nMatthias Claudius (1740-1815)",
			"Einen Wahn verlieren macht weiser als eine Wahrheit finden.\nLudwig Börne (1786-1837)",
			"Eine tausend Meilen lange Reise beginnt mit einem einzelnen Schritt.\nLaotse (um 600 v.Chr.)",
			"Ein Mann, der die Wahrheit spricht, braucht ein schnelles Pferd.\nKonfuzius (um 500 v.Chr.)",
			"Erfahrungen vererben sich nicht, jeder muss sie allein machen.\nKurt Tucholsky (1890-1935)",
			"Es gibt ebensowenig hundertprozentige Wahrheit wie hundertprozentigen Alkohol.\nSigmund Freud (1856-1939)",
			"Es ist leicht den Hass, schwer die Liebe, am schwersten Gleichgültigkeit zu verbergen.\nLudwig Börne (1786-1837)",
			"Es ist nicht von Bedeutung wie langsam du gehst, solange du nicht stehen bleibst.\nKonfuzius (um 500 v.Chr.)",
			"Ich beschäftige mich nicht mit dem, was getan worden ist. Mich interessiert, was getan werden muss.\nMarie Curie (1867-1934)",
			"Jeder muss sein eigenes Leben leben und seinen eigenen Preis dafür zahlen.\nOscar Wilde (1854-1900)",
			"Nur wer die Kunst des Vergessens erlernte, hat Lebenskunst gelernt.\nOscar Wilde (1854-1900)",
			"Sobald du dir vertraust, sobald weißt du zu leben.\nJohann Wolfgang von Goethe (1749-1832)",
			"Weise Lebensführung gelingt keinem Menschen durch Zufall. Man muss, solange man lebt, lernen, wie man leben soll.\nSeneca (um Christi Geburt)",
			"Wenn du das Leben aushalten willst, richte dich auf den Tod ein.\nSigmund Freud (1856-1939)",
			"Wer kein Marktschreier sein will, muss dem Podium fernbleiben. Steigt man hinauf, so muss man es sein, um nicht von der Versammlung mit Steinen beworfen zu werden.\nNicolas-Sebastien Chamfort (1741-1794)",
			"Wer sich nicht selbst helfen will, dem kann niemand helfen.\nHeinrich Pestalozzi (1746-1827)",
			"Wo viel Gefühl ist, ist auch viel Leid.\nLeonardo da Vinci (1452-1519)",
			"Bereit sein ist alles.\nWilliam Shakespeare (1564-1616)",
			"Das Leben ist bezaubernd. Man muss es nur durch die richtige Brille sehen.\nAlexandre Dumas (1802-1870)",
			"Das Glück deines Lebens hängt ab von der Beschaffenheit deiner Gedanken.\nMark Aurel (121-180)",
			"Das Leben ist nicht zu Ende, nur weil ein Traum nicht in Erfüllung geht.\nEs hat nur einen Weg versperrt, damit man einen anderen sucht.\nChinesische Weisheit",
			"Ein Freund mehr, ein Weg mehr.\nChinesisches Sprichwort",
			"Es ist besser, ein kleines Licht anzuzünden, als über die Dunkelheit zu fluchen.\nKonfuzius (551-479 v.Chr.)",
			"Es sind nicht unsere Füße die uns bewegen, es ist unser Denken.\nChinesisches Sprichwort",
			"Gar mancher Schwierigkeit entweicht man durch das hübsche Wort 'vielleicht'.\nWilhelm Busch (1832-1908)",
			"Gibt es schließlich eine bessere Form mit dem Leben fertig zu werden, als mit Liebe und Humor?\nCharles Dickens (1812-1870)",
			"Lebe so, dass nichts vorkommt, was nicht auch dein Feind wissen dürfte.\nSeneca (geboren um Christi Geburt)",
			"O schätze mich nicht gering, ich bin nicht arm. Arm ist, wer viele Dinge wünscht.\nLeonardo da Vinci (1452-1519)",
			"Sei wie ein Fels, an dem sich beständig die Wellen brechen.\nMark Aurel (121-180)",
			"Das Nichthaben ist der Anfang allen Denkens.\nRobert Musil (1880-1942)",
			"Um durch die Welt zu kommen, ist es zweckmäßig, einen großen Vorrat an Vorsicht und Nachsicht mitzubringen.\nArthur Schopenhauer (1788-1860)",
			"Um klar zu sehen, genügt oft nur ein Wechsel der Blickrichtung.\nAntoine de Saint-Exupery (1900-1944)",
			"Verdirb dir nicht die Freude an dem was du hast, indem du dich nach etwas sehnst, was du nicht hast.\nEpikur von Samos (um 300 v.Chr.)",
			"Wenn der Wind der Veränderung weht, bauen die einen Mauern und die anderen Windmühlen.\nChinesische Weisheit",
			"Wenn du ein Problem hast, versuche es zu lösen. Kannst du es nicht lösen, dann mache kein Problem daraus.\nBuddha (um 500 v.Chr.)",
			"Wenn du helle Dinge denkst, ziehst du helle Dinge an dich heran.\nPrentice Mulford (1834-1891)",
			"Wenn man das Dasein als eine Aufgabe betrachtet, dann vermag man es immer zu ertragen.\nMarie von Ebner-Eschenbach (1830-1916)",
			"Wer das Leben liebt und den Tod nicht scheut, geht frühlich durch die sinkende Zeit.\nTheodor Körner (1791-1813)",
			"Wie viele Dinge es doch gibt, die ich nicht brauche!\nSokrates (um 450 v.Chr.)",
			"Willst du das Leben leicht haben, so bleibe immer bei der Herde.\nFriedrich Nietzsche (1844-1900)",
			"Denke lieber an das, was du hast, als an das,\nwas dir fehlt! Mark Aurel (121-180)",
			"An unmöglichen Dingen soll man selten verzweifeln, an schweren nie.\nJohann Wolfgang von Goethe (1749-1832)",
			"Aus jedem Tag das Beste zu machen, das ist die größte Kunst.\nHenry David Thoreau (1817-1862)",
			"Beginne jeden Morgen mit einem guten Gedanken!\nCarl Hilty (1833-1909)",
			"Das 'Ich', das alles möglich macht.\nJohann Gottlieb Fichte (1762-1814)",
			"Mach nur die Augen auf und alles ist schön.\nHans Thoma (1839-1924)",
			"Der Glaube lässt dich Unmögliches versuchen. Der Zweifel hingegen hält selbst vom Möglichen ab.\nMarcel Baumert",
			"Die Tränen lassen nichts gelingen, wer schaffen will, muss frühlich sein.\nTheodor Fontane (1819-1898)",
			"Es ist nicht auszudenken, was Gott aus den Bruchstücken unseres Lebens machen kann, wenn wir sie ihm ganz überlassen.\nBlaise Pascal (1623-1662)",
			"Fasse frischen Mut! Solange ist keine Nacht, dass endlich nicht der helle Morgen lacht.\nWilliam Shakespeare (1564-1616)",
			"Gib jedem Tag die Chance, der schönste deines Lebens zu werden.\nMark Twain (1835-1910)",
			"Hat man den Wind gegen sich, so ist das nur ein Grund, um so störker auszuschreiten.\nJulius Langbehn (1851-1907)",
			"Fehlt es am Wind, so greife zum Ruder.",
			"und du hast den besten Wanderstab, um Abgründe zu überspringen. Lisa Wenger (1858-1941)\nSprichwort",
			"Mache das Beste aus dir, etwas Besseres kannst du nicht tun.\nRalph Waldo Emerson (1803-1882)",
			"Man kann zwar kein neues Leben beginnen, aber täglich einen neuen Tag.\nHenry David Thoreau (1817-1862)",
			"Man muss anfangen und aufhören können, wann man will. Wenn man erst will, dann kann man auch.\nNovalis (1772-1801)",
			"Mut steht am Anfang des Handelns, Glück am Ende.\nDemokrit (um 400 v.Chr.)",
			"Nicht das Hinfallen ist schlimm, sondern es ist schlimm, wenn man dort liegenbleibt, wo man hingefallen ist.\nSokrates (um 450 v.Chr.)",
			"Niemand weiß, was er kann, bevor er es versucht.\nBaum wächst durch Steinwand\nWo ein Wille ist, ist auch ein Weg.\nPublilius Syrus (lebte v.Chr.)",
			"Nicht mit brennenden Kerzen entzündet man die Welt, sondern nur mit brennenden Herzen.\nPeter Lippert (1879-1936)",
			"Nur die Sache ist verloren, die man selber aufgibt.\nGotthold Ephraim Lessing (1729-1781)",
			"Sammle dir jeden Tag etwas Ewiges, das dir kein Tod raubt, das den Tod und das Leben dir lieblicher jeden Tag macht.\nJohann Kaspar Lavater (1741-1801)",
			"Was man oft versucht und immer will, das gelingt dann einmal.\nSigmund Freud (1856-1939)",
			"Was vor uns liegt und was hinter uns liegt, sind Kleinigkeiten zu dem, was in uns liegt. Und wenn wir das, was in uns liegt, nach außen in die Welt tragen, geschehen Wunder.\nHenry David Thoreau (1817-1862)",
			"Wenn dir das Leben in den Arsch tritt, dann nutze den Schwung, um vorwärts zu kommen.",
			"Wenn man's nur versucht, so geht's. Das heißt: mitunter, doch nicht stets.\nWilhelm Busch (1832-1908)",
			"Wenn wir uns von unseren Träumen leiten lassen, wird der Erfolg all unsere Erwartungen übertreffen.\nHenry David Thoreau (1817-1862)",
			"Wo der Mensch gefallen ist, dort muss er sich wieder aufrichten, um wieder hochzukommen.\nAugustinus von Hippo (354-430)",
			"Auch in reißenden Strömen findet man Felsen des Haltes.",
			"Eigentlich hat's ja nicht viel auf sich mit dem besten Pessimismus. An dem Glücklichen gleitet er ab wie Wasser an der pomadisierten Ente, und der Unglückliche weiß ohne Weiteres Bescheid.\nWilhelm Busch (1832-1908)",
			"Arbeit, Sorg und Herzeleid, ist der Erde Alltagskleid.\nSprichwort",
			"Der Pessimist sieht in jeder Chance eine Bedrohung. Der Optimist in jeder Bedrohung eine Chance.\nChinesische Weisheit",
			"Die glücklichen Pessimisten! Welche Freude empfinden sie, so oft sie bewiesen haben, dass es keine Freude gibt!\nMarie von Ebner-Eschenbach (1830-1916)",
			"Ein Pessimist ist ein Mensch, der sich über den Lärm beschweren würde, wenn das Glück anklopft.\nUnbekannt",
			"Ein Pessimist ist jemand, der vorzeitig die Wahrheit erzählt.\nCyrano de Bergerac (1619-1655)",
			"Es gibt Tage da verliert man und es gibt Tage da gewinnen die Anderen!\nUnbekannt",
			"Glück, Freiheit sind Negationen der Wirklichkeit.\nWilhelm Busch (1832-1908)",
			"Man kann über alles spötteln, weil alles eine Kehrseite hat.\nCharles de Montesquieu (1689-1755)",
			"Pessimismus hat auf der Erde kein Recht. Wer freiwillig am Leben bleibt, erklärt sich einverstanden, zufrieden und mitschuldig.\nWalther Rathenau (1867-1922)",
			"Pessimisten sind die wahren Lebenskünstler, denn nur sie erleben angenehme Überraschungen.\nMarcel Proust (1871-1922)",
			"Was du auch tust, du wirst es bereuen.\nSokrates (um 430 v.Chr.)",
			"Wenn Unglück durch die Welt gewittert und in den Tiefen uns erschättert, dem Pessimisten bleibt die Freude: Es fügt sich in sein Lehrgebäude.\nOskar Blumenthal (1852-1917)",
			"Wer sagt, die ganze Welt sei schlecht, der hat wohl nur so ziemlich recht.\nWilhelm Busch (1832-1908) ",
			"Das Übel, das uns trifft, ist selten oder nie so schlimm, als das, welches wir befürchten.\nFriedrich Schiller (1759-1805)",
			"Das Urteil der Menge mache dich immer nachdenkend, doch niemals verzagt.\nAugust von Platen (1796-1835)",
			"Dein Auge kann die Welt trüb oder hell dir machen: Wie du sie ansiehst, wird sie weinen oder lachen.\nFriedrich Rückert (1788-1866)",
			"Die einzige Begrenzung, das Morgen zu verwirklichen, werden unsere Zweifel von heute sein.\nFranklin D. Roosevelt (1882-1945)",
			"Drei Dinge helfen, die Mühen des Lebens zu tragen: Die Hoffnung, der Schlaf und das Lachen.\nImmanuel Kant (1724-1804)",
			"Eine Kleinigkeit tröstet uns, weil eine Kleinigkeit uns betrübt.\nBlaise Pascal (1623-1662)",
			"Ein Optimist ist ein Mensch, der ein Dutzend Austern bestellt, in der Hoffnung, sie mit der Perle, die er darin findet, bezahlen zu können.\nTheodor Fontane (1819-1898)",
			"Es hat immer in meiner Natur gelegen, mir zuzurufen: 'Vorwärts', selbst wenn vorwärts Niederlage bedeutete.\nRomain Rolland (1866-1944)",
			"Es kommt oft anders. Das ist ein wahres Wort und zugleich auch ein guter Trost, der dem Menschen in seinem Erdenleben auf den Weg gegeben worden ist.\nWilhelm Raabe (1831-1910)",
			"Genieße das Leben ständig, denn du bist länger tot als lebendig!\nSprichwort",
			"Heiterkeit des Herzens schließt wie der Frühling alle Blüten des Inneren auf.\nJean Paul (1763-1825)",
			"Heiterkeit ist der Ursprung aller glücklichen Einfälle.\nLuc de Clapiers, Marquis de Vauvenargues (1715-1747)",
			"Jeder bedarf allezeit eines gewissen Quantums an Sorge oder Schmerz oder Not, wie das Schiff des Ballastes, um fest und gerade zu stehen.\nArthur Schopenhauer (1788-1860)",
			"Lass nur die Sorge sein, das gibt sich alles schon, und fällt der Himmel ein, kommt doch eine Lerche davon.\nJohann Wolfgang von Goethe (1749-1832)",
			"Lebe so, dass deine Geschichte Zukunft hat.",
			"Dem Heiteren erscheint die Welt auch heiter. \n(Johann Wolfgang von Goethe 1749-1832)",
			"Mag auch das Böse sich noch so sehr vervielfachen, niemals vermag es das Gute ganz aufzuzehren.\nThomas von Aquin (1225-1274)",
			"Man muss sein Leben aus dem Holze schnitzen, das man hat, und wenn es krumm und knorrig wäre.\nTheodor Storm (1817-1888)",
			"Man muss versuchen, sich über nichts zu betrüben, und alles was geschieht, als das Beste anzusehen.\nBlaise Pascal (1623-1662)",
			"Nenne dich nicht arm, weil deine Träume nicht in Erfüllung gegangen sind,\nwirklich arm ist nur, wer nie geträumt hat!\nMarie von Ebner-Eschenbach (1830-1916)",
			"Nur das frühliche Herz allein ist fähig, Wohlgefallen am Guten zu empfinden.\nImmanuel Kant (1724-1804)",
			"Pessimisten stehen im Regen, Optimisten duschen unter den Wolken.\nUnbekannt",
			"Selbst wenn ich wüsste, dass morgen die Welt unterginge, würde ich heute noch ein Apfelbäumchen pflanzen.\nMartin Luther (1483-1546)",
			"Wahrheitsliebe zeigt sich darin, dass man überall das Gute zu schätzen weiß.\nJohann Wolfgang von Goethe (1749-1832)",
			"Wenn du am Morgen erwachst, denke daran, was für ein Schatz es ist zu leben, zu atmen und sich freuen zu können.\nMark Aurel (121-180)",
			"Wenn einem das Wasser bis zum Halse steht, sollte man nicht auch noch den Kopf hängen lassen.",
			"Wenn ihr euch nur selbst vertraut, vertrauen euch die andren Seelen.\nJohann Wolfgang von Goethe (1749-1832)",
			"Zuviel Vertrauen ist häufig eine Dummheit, zuviel Misstrauen ist immer ein Unglück.\nJohann Nestroy (1801-1862)",
			"Der Weg ist das Ziel.\nKonfuzius (um 500v.Chr.)",
			"Die Summe unseres Lebens sind die Stunden, in denen wir liebten.\nWilhelm Busch (1832-1908)",
			"Du kannst dein Leben nicht verlängern, noch verbreitern, nur vertiefen.\nGorch Fock (1880-1916)",
			"Ich fühle, dass Kleinigkeiten die Summe des Lebens ausmachen.\nCharles Dickens (1812-1870)",
			"Im Grunde sind es immer die Verbindungen mit Menschen, die dem Leben seinen Wert geben.\nWilhelm von Humboldt (1767-1835)",
			"Leben, das ist das Allerseltenste in der Welt, die meisten Menschen existieren nur.\nOscar Wilde (1854-1900)",
			"Leben wir allein nur für dieses Leben, so sind wir die elendsten aller erschaffenen Wesen.\nWilhelm von Humboldt (1767-1835)",
			"Lebe, wie du, wenn du stirbst, wünschen wirst gelebt zu haben.\nChristian Fürchtegott Gellert (1715-1769)",
			"Man erlebt nicht das, was man erlebt, sondern wie man es erlebt.\nWilhelm Raabe (1831-1910)",
			"Man muss eine Aufgabe vor sich sehen und nicht ein geruhsames Leben.\nLew Nikolajewitsch Tolstoi (1828-1910)",
			"Man muss immer etwas Neues machen, um etwas Neues zu sehen.\nGeorg Christoph Lichtenberg (1742-1799)",
			"Mit dem Leben ist es wie mit einem Theaterstück, es kommt nicht darauf an wie lange es dauert, sondern wie bunt es ist.\nSeneca (geboren um Christi Geburt)",
			"Man lebt nicht einmal einmal.",
			"Nicht was wir erleben, sondern wie wir es empfinden, macht unser Schicksal aus. (Marie von Ebner-Eschenbach 1830-1916)\nKarl Kraus (1874-1936)",
			"Nicht der Mensch hat am meisten gelebt, welcher die höchsten Jahre zählt, sondern derjenige, welcher sein Leben am meisten empfunden hat.\nJean-Jacques Rousseau (1712-1778)",
			"Nur der Denkende erlebt sein Leben, an den Gedankenlosen zieht es vorbei.\nMarie von Ebner-Eschenbach (1830-1916)",
			"Nur der Tag bricht an, für den wir wach sind.\nHenry David Thoreau (1817-1862)",
			"Sein Leben mit einer gewissen Allgegenwärtigkeit des Grabes verbinden ist das Gesetz des Weisen und das des Asketen.\nVictor Hugo (1802-1885)",
			"Trenne dich nicht von deinen Illusionen. Wenn sie verschwunden sind, wirst du weiter existieren, aber aufgehört haben zu leben.\nMark Twain (1835-1910)",
			"Wahre Universalität besteht nicht darin, dass man vieles weiß, sondern dass man vieles liebt.\nJacob Burckhardt (1818-1897)",
			"Wenn der Tag und die Nacht so sind, dass du sie mit Freude begrüßt, und das Leben dir duftet wie Blumen und würzige Kräuter, das ist dein Erfolg.\nHenry David Thoreau (1817-1862)",
			"Wer nicht mehr strebt und nicht mehr lernt, der lasse sich begraben.\nErnst von Feuchtersleben (1806-1849)",
			"Irgendwo leuchtet immer ein Licht!",
			"Sinnlos ist ein Leben ohne Sinn für Unsinn!\nAutor unbekannt",
			"Gottes Liebe ist immer größer, als unser Leid sein kann.\nFranz von Sales (1567-1622)",
			"Im Reiche Gottes gibt es nur übergänge, keinen Tod.\nFriedrich Lienhard (1865-1929)",
			"Wider den Schmerz dich zu vermauern, ist so verkehrt wie maßlos trauern.\nEmanuel Geibel (1815-1884)",
			"Mit dem Tod seiner Eltern verliert man ein Stück seiner Vergangenheit,\nmit dem des Partners einen Teil seiner Gegenwart,\nmit dem seines Kindes einen Teil seiner Zukunft.\nAntoine de Saint-Exupéry (1900-1944)",
			"Die Menschen denken, dass Kummer Schmerz sei; aber dem ist nicht so. Kummer, das Sich-Versenken in eine stille Erinnerung an das, was war, aber nicht mehr ist, ist eine Freude, ein Trost, ein Segen.\nFriedrich Max Müller (1823-1900)",
			"Es nimmt der Augenblick, was Jahre gegeben.\nJohann Wolfgang von Goethe (1749-1832)",
			"Für einen Vater, dessen Kind stirbt, stirbt die Zukunft. Für ein Kind, dessen Eltern sterben, stirbt die Vergangenheit.\nBerthold Auerbach (1812-1882)",
			"Meist belehrt erst der Verlust uns über den Wert der Dinge.\nArthur Schopenhauer (1788-1860)",
			"Tröste mich aus deinem Herzen, nicht aus deiner Bibel. In der meinen steht dasselbe.\nGeorg (Karl, Friedrich, Theodor, Ludwig) Baron von Örtzen (1829-1910)",
			"Der Tod ist das Tor zum Licht am Ende eines mühsam gewordenen Weges.\nFranz von Assisi (1182-1226)",
			"Der Tod ist nicht für schlimm zu achten, dem ein gutes Leben vorangegangen.\nAugustinus von Hippo (354-430)",
			"Die letzten Stichworte in einem richtig gefährten menschlichen Leben müssen Friede und Güte heißen.\nCarl Hilty (1833-1909)",
			"Nicht den Tod sollte man fürchten, sondern dass man nie beginnen wird zu leben.\nMarcus Aurelius (121-180)",
			"Nicht verloren, nur vorangegangen.\nMichelangelo (1475-1564)",
			"Wer stirbt, bezahlt alle seine Schulden.\nWilliam Shakespeare (1564-1616)",
			"Wir wissen nichts vom Leben, wie könnten wir etwas vom Tod wissen?\nKonfuzius (551-479 v.Chr.)",
			"Bedenke stets dir im Unglück Gleichmut zu bewahren.\nHoraz (65-8 v.Chr.)",
			"Unglücklich ist, wer vor der Zukunft Angst hat.\nSeneca (um Christi Geburt)",
			"Weinen ist Mitleid mit sich selbst.\nArthur Schopenhauer (1788-1860)",
			"Wie das übermaß der Freude oft in Traurigkeit endigt, so folgen hingegen neue Freuden auf das überstandene Leid.\nGiovanni Boccaccio (1313-1375)",
			"Anteilnehmende Freundschaft macht das Glück strahlender und erleichtert das Unglück.\nMarcus Tullius Cicero (106-43 v.Chr.)",
			"Dem Traurigen ist jede Blume ein Unkraut, dem Glücklichen jedes Unkraut eine Blume.",
			"Entschlossenheit im Unglück ist immer der halbe Weg zur Rettung.\nJohann Heinrich Pestalozzi (1746-1827)",
			"Ich bin fest überzeugt, dass sich die Menschen selbst mehr Unheil zufügen, als ihnen der Teufel je antun könnte.\nLord Byron (1788-1824)",
			"Man hört bei den größten Übeln, die die Menschen einander antun, hintennach immer das Wort:\nHätte man auch miteinander geredet.\nHeinrich Pestalozzi (1746-1827)",
			"Unglücklich ist, wer vor der Zukunft Angst hat.\nSeneca (um Christi Geburt)",
			"Wenn ich auf mein Unglück trete, stehe ich höher.\nFriedrich Hölderlin (1770-1843)",
			"Wer etwas wert ist, den machen Erfahrungen und Unglück besser.\nJohann Heinrich Pestalozzi (1746-1827)",
			"Wir verwenden unseren Geist besser dazu,\ndas Unheil zu ertragen, das uns trifft,\nals uns um das zu sorgen,\nwelches uns noch treffen kann.\nLa Rochefoucauld (1613-1680) ",
			"Das Schicksal des Menschen wird durch die Weise bestimmt, in welcher er sich selbst zu betrachten fähig ist.\nHenry David Thoreau (1817-1862)",
			"Das Schicksal nimmt nichts, was es nicht gegeben hat.\nLucius Annaeus Seneca (um Christi Geburt)",
			"Des Schicksals Zwang ist bitter.\nChristoph Martin Wieland (1733-1813)",
			"Es gibt kein Schicksal. Es gibt nur falsche Reaktionen auf Ereignisse, die ich durch meine falschen Reaktionen manifestiert habe.\nThomas Pfitzer (geb. 1961)",
			"Schicksal ist, wenn du etwas findest, was du nie gesucht hast und dann aber feststellst, dass du nie etwas anderes wolltest!",
			"Was die Leute gemeiniglich das Schicksal nennen, sind meistens nur ihre eigenen dummen Streiche.\nArthur Schopenhauer (1788-1860)",
			"Wenn etwas gewaltiger ist als das Schicksal, so ist es der Mut, der es unerschättert trägt.\nEmanuel Geibel (1815-1884)",
			"Alles im Leben hat seinen Preis, auch Dinge, von denen man glaubt, man bekommt sie geschenkt.\nTheodore Fontane (1819-1898)",
			"Der Tod macht den Tag erst lebenswert.\naus Pirates Of The Caribbean 3",
			"Das Leben ist kurz, aber man hat immer Zeit für Höflichkeit.\nRalph Waldo Emerson (1803-1882)",
			"Das Leben ist wie eine Kletterwand, nicht immer ist der kürzeste auch der beste Weg.",
			"Das Leben wär nur halb so nett, wenn keiner einen Vogel hätt.",
			"Der Mensch, der allein reist, kann sich heute auf den Weg machen; doch wer mit einem anderen reist, muss warten, bis dieser bereit ist.\nHenry David Thoreau (1817-1862)",
			"Egoismus besteht nicht darin, dass man sein Leben nach seinen Wünschen lebt,\nsondern darin, dass man von anderen verlangt, dass sie so leben, wie man es wünscht.\nOscar Wilde (1954-1900)",
			"Leben und leben lassen.\nJohann Wolfgang von Goethe (1749-1832)",
			"Nicht mitzuhassen, mitzulieben bin ich da.\nSophokles (um 450 v.Chr.)",
			"Seit dem der Tod aufgekommen ist, ist man seines Lebens nicht mehr sicher.\nSprichwort",
			"Verschiebe nicht auf morgen, was genau so gut auf übermorgen verschoben werden kann.\nMark Twain (1835-1910)",
			"Zwei Lebensstützen brechen nie, Gebet und Arbeit heißen sie.\nKirchenspruch",
			"An zwei Dinge muss man sich gewöhnen, um das Leben erträglich zu finden:\nDie Unbilden der Zeit und die Ungerechtigkeiten der Menschen.\nNicolas-Sebastien Chamfort (1741-1794)",
			"Bald, und du hast alles vergessen,\nbald, und alles hat dich vergessen.\nMarc Aurel (121-180)",
			"Das ganze Leben ist ein ewiges Wiederanfangen.\nHugo von Hofmannsthal (1874-1929)",
			"Dass man die größten Wunder zu Hause erlebt, das lernt man erst in der Fremde erkennen.\nWilhelm Raabe (1831-1910)",
			"Leben heißt leiden, aber der tapfere Mensch kämpft beständig, um schließlich doch Herr über sich selbst zu bleiben.\nNapoleon Bonaparte (1769-1821)",
			"Wandel und Wechsel liebt, wer lebt.\nRichard Wagner (1813-1883)",
			"Wer das Leben nicht schützt, verdient es nicht.\nLeonardo da Vinci (1452-1519)",
			"Wer nicht viel tragen kann, wohltun und viel vergeben, versteht die Weisheit nicht und nicht die Kunst zu leben.\nJohann Kaspar Lavater (1741-1801)",
			"Wir sind, was wir denken.\nAlles was wir sind, entsteht aus unseren Gedanken.\nMit unseren Gedanken formen wir die Welt.\nBuddha (lebte v.Chr.)",
			"Wonach soll man am Ende trachten? Die Welt zu kennen und sie nicht verachten.\nGeorg Christoph Lichtenberg (1742-1799)",
			"Angst beflügelt den eilenden Fuß.\nFriedrich Schiller (1759-1805)",
			"Angst ist für die Seele ebenso gesund wie ein Bad für den Körper.\nMaksim Gorki (1868-1936)",
			"Angst verhindert den Ausgang aus der selbstverschuldeten Unmündigkeit und ist dadurch ein Feind der Aufklärung.\nImmanuel Kant (1724-1804)",
			"Die Wurzel des Optimismus ist Angst.\nWolkenverhangener Himmel	\nWenn der Himmel brennt,\nhat die Erde Feuer gefangen. \nOscar Wilde (1854-1900)",
			"Aus Angst, mit Wenigem auskommen zu müssen, lässt sich der Durchschnittsmensch zu Taten hinreißen, die seine Angst erst recht vermehren.\nEpikur (um 300 v.Chr.)",
			"Das ist nicht der Tapferste, der sich nie gefürchtet, sondern der die Furcht überwunden hat.\nSprichwort",
			"Feigheit ist manchmal ein Zeichen von Klugheit.\nSprichwort",
			"Das Schlimmste fürchten, heilt oft das Schlimmste.\nWilliam Shakespeare (1564-1616)",
			"Den größten Fehler, den man im Leben machen kann, ist immer Angst zu haben, einen Fehler zu machen.\nDietrich Bonhoeffer (1906-1945)",
			"Der Edle hat Angst um andere, der Gemeine um sich selber.\nErnst Paul (1866-1933)",
			"Der Furchtsame erschrickt vor der Gefahr, der Feige in ihr, der Mutige nach ihr.\nJean Paul (1763-1825)",
			"Der Mut wächst mit der Gefahr; die Kraft erhebt sich im Drang.\nFriedrich Schiller (1759-1805)",
			"Die Menschen werden jenes Ding verfolgen, vor dem sie am meisten Angst haben.\nLeonardo da Vinci (1452-1519)",
			"Ein Feigling ist ein Mensch, bei dem der Selbsterhaltungstrieb normal funktioniert.\nAmbrose Bierce (1842-1914)",
			"Es ist besser die Angst als die Hoffnung zu haben.\nTheodor Fontane (1819-1898)",
			"Furcht besiegt mehr Menschen als irgendetwas anderes auf der Welt.\nRalph Waldo Emerson (1803-1882)",
			"Jede Furcht rührt daher, dass wir etwas lieben.\nThomas von Aquin (1224-1274)",
			"Man braucht nichts im Leben zu fürchten, man muss nur alles verstehen.\nMarie Curie (1867-1934)",
			"Nicht weil es schwer ist, wagen wir es nicht, sondern weil wir es nicht wagen, ist es schwer.\nSeneca (um Christi Geburt)",
			"Niemanden kränken, niemanden beeinträchtigen wollen kann ebensowohl das Kennzeichen einer gerechten als einer ängstlichen Sinnesart sein.\nFriedrich Nietzsche (1844-1900)",
			"Tue nichts im Leben, was dir Angst machen muss, wenn es dein Nächster bemerkt.\nEpikur von Samos (um 300 v.Chr.)",
			"Was soll der fürchten, der den Tod nicht fürchtet?\nFriedrich Schiller (1759-1805)",
			"Wer nichts fürchtet, ist nicht weniger mächtig als der, den alles fürchtet.\nFriedrich Schiller (1759-1805)",
			"Wer nicht täglich seine Furcht überwindet, hat die Lektion des Lebens nicht gelernt.\nRalph Waldo Emerson (1803-1882)",
			"Begeht der Kluge eine Dummheit, so ärgert er sich über diese; der Weise aber lächelt über sich.\nJacob Lorenz (1874-1939)",
			"Das Geheimnis des Agitators ist, sich so dumm zu machen, wie seine Zuhörer sind, damit sie glauben, sie seien so gescheit wie er.\nKarl Kraus (1874-1936)",
			"Das Recht auf Dummheit gehört zur Garantie der freien Entfaltung der Persönlichkeit.\nMark Twain (1835-1910)",
			"Das sind die Weisen, die durch Irrtum zur Weisheit reisen. Die bei dem Irrtum verharren, das sind die Narren.\nFriedrich Rückert (1788-1866)",
			"Der ist ein Tor, der nicht der Weisheit folgt.\nWilliam Shakespeare (1564-1616)",
			"Der Klügere gibt nach. Eine traurige Wahrheit, sie begründet die Weltherrschaft der Dummheit.\nMarie von Ebner-Eschenbach (1830-1916)",
			"Der Narr tut, was er nicht lassen kann, der Weise lässt, was er nicht tun kann.\nChinesisches Sprichwort",
			"Der schlimmste aller Fehler ist, sich keines solchen bewusst zu sein.\nThomas Carlyle (1795-1881)",
			"Der Tor braucht einen Keulenschlag, wo dem Weisen ein Wink genügen mag.\nPersische Weisheit",
			"Der Unverstand ist die unbesiegbarste Macht auf Erden.\nAnselm Feuerbach (1829-1880)",
			"Der Vorteil der Klugheit besteht darin, dass man sich dumm stellen kann. Das Gegenteil ist schon schwieriger.\nKurt Tucholsky (1890-1935)",
			"Der Wunsch klug zu erscheinen, verhindert oft, es zu werden.\nFrancois de La Rochefoucauld (1613-1680)",
			"Die böseste unserer Krankheiten ist die, unser Leben zu verachten.\nMichel de Montaigne (1533-1592)",
			"Die Dummheit geht oft Hand in Hand mit Bosheit.\nHeinrich Heine (1797-1856)",
			"Die Liebe ist der einzige Weg, auf dem selbst die Dummen zu einer gewissen Größe gelangen.\nHonore de Balzac (1799-1850)",
			"Die menschliche Dummheit ist international.\nKurt Tucholsky (1890-1935)",
			"Dumme Gedanken hat jeder, aber der Weise verschweigt sie.\nWilhelm Busch (1832-1908)",
			"Dumme rennen, Kluge warten, Weise gehen in den Garten.\nRabindranath Thakur (1861-1941)",
			"Dummheit ist auch eine natürliche Begabung.\nWilhelm Busch (1832-1908)",
			"Dummheit kennt keine Grenzen, aber verdammt viele Menschen!\nSprichwort",
			"Dumm ist, wer Dummes tut.",
			"Einen Dummkopf betrügen heißt, den Verstand rächen.\nGiacomo Casanova (1725-1798)",
			"Ein Kluger bemerkt alles, ein Dummer macht über alles seine Bemerkungen.\nHeinrich Heine (1797-1856)",
			"Erfahrung ist der Name, mit dem jeder seine Dummheiten bezeichnet.\nOscar Wilde (1854-1900)",
			"Erfahrung ist die einzige Schule, in der auch Dummköpfe lernen können.\nBenjamin Franklin (1706-1790)",
			"Es fällt uns sehr schwer, denjenigen, der uns bewundert, für einen Dummkopf zu halten.\nMarie von Ebner-Eschenbach (1830-1916)",
			"Es gibt Leute, die geizen mit ihrem Verstand wie andere mit ihrem Geld.\nLudwig Börne (1786-1837)",
			"Es ist keine Schande nichts zu wissen, wohl aber, nichts lernen zu wollen.\nSokrates (um 450v.Chr.)",
			"Es ist nichts schrecklicher als eine tätige Unwissenheit.\nJohann Wolfgang von Goethe (1749-1832)",
			"Es wäre dumm, sich über die Welt zu ärgern. Sie kümmert sich nicht darum.\nMarcus Aurelius (121-180)",
			"Gegen eine Dummheit, die gerade in Mode ist, kommt keine Klugheit auf.\nTheodor Fontane (1819-1898)",
			"Gescheit gedacht und dumm gehandelt, so bin ich meine Tage durchs Leben gewandelt.\nFranz Grillparzer (1791-1872)",
			"Gesellschaftlich ist kaum etwas so erfolgreich wie Dummheit mit guten Manieren.\nVoltaire (1694-1778)",
			"Immer lernt der Kluge vom Dummen mehr als der Dumme vom Klugen.\nPeter Rosegger (1843-1918)",
			"Jeder Fehler erscheint unglaublich dumm, wenn andere ihn begehen.\nGeorg Christoph Lichtenberg (1742-1799)",
			"Lang ist die Nacht für den, der nicht schläft;\nLang ist ein Kilometer für den Müden;\nLang ist das Leben für den Toren.\nLeo Tolstoi (1828-1910)",
			"Lieber schlecht als dumm. Schlechte Menschen sind auch ab und zu gut. Dumme sind immer bloß dumm.\nAlexander Dumas (1802-1870)",
			"Hoffnung ist die Wiese, auf der die Narren grasen.\nSprichwort",
			"Man führt immer das Herz ins Treffen, wenn man eine Dummheit gemacht hat.\nStendhal (1783-1842)",
			"Man kann wetten, dass jede öffentliche Meinung, jede allgemeine Konvention eine Dummheit ist, denn sie hat der großen Menge gefallen.\nNicolas Chamfort (1741-1794)",
			"Man muss schon etwas wissen, um verbergen zu können, dass man nichts weiß.\nMarie von Ebner-Eschenbach (1830-1916)",
			"Menschlich war es zu irren, teuflisch ist es, leidenschaftlich im Irrtum zu verharren.\nAugustinus von Hippo (354-430)",
			"Nur die Weisesten und die Dümmsten können sich nicht ändern.\nKonfuzius (um 500 v.Chr.)",
			"Um ernst zu sein, genügt Dummheit, während zur Heiterkeit ein großer Verstand unerlässlich ist.\nWilliam Shakespeare (1564-1616)",
			"Unwissenden scheint, wer Weises sagt, nicht klug zu sein.\nEuripides (um 450 v.Chr.)",
			"Unwissende werfen Fragen auf, welche von Wissenden vor tausend Jahren schon beantwortet sind.\nJohann Wolfgang von Goethe (1749-1832)",
			"Unwissenheit ist die Nacht des Geistes, eine Nacht ohne Mond und Sterne.\nKonfuzius (um 500 v.Chr.)",
			"Wenn 10 Millionen Menschen etwas Dummes sagen, bleibt es trotzdem eine Dummheit.\nAnatole France (1844-1924)",
			"Wer einen Fehler macht und ihn nicht korrigiert, begeht einen zweiten.\nKonfuzius (um 500 v.Chr.)",
			"Wer nichts weiß, muss alles glauben.\nMarie von Ebner-Eschenbach (1830-1916)",
			"Wer sich über Kritik ärgert, gibt zu, dass sie verdient war.\nTacitus (um 100 n.Chr.)",
			"Allein zu sein! Drei Worte, leicht zu sagen, und doch so schwer, so endlos schwer zu tragen.\nAdelbert von Chamisso (1781-1838)",
			"Alle unsere Leiden kommen daher, dass wir nicht allein sein können.\nArthur Schopenhauer (1788-1860)",
			"Bist du allein mit dir, so bist du mit dem Genius.\nBettina von Arnim (1785-1859)",
			"Die Einsamkeit macht uns härter gegen uns und sehnsüchtiger gegen die Menschen, in beiden verbessert sie den Charakter.\nFriedrich Nietzsche (1844-1900)",
			"Eines ist Verlassenheit, ein anderes Einsamkeit.\nFriedrich Nietzsche (1844-1900)",
			"Ein geistreicher Mensch hat, in gänzlicher Einsamkeit an seinen eigenen Gedanken und Phantasien vortreffliche Unterhaltung.\nArthur Schopenhauer (1788-1860)",
			"Einsamkeit ist das Los aller hervorragender Geister.\nArthur Schopenhauer (1788-1860)",
			"Einsamkeit, verbunden mit einem klaren, heiteren Bewusstsein ist, ich behaupte es, die einzig wahre Schule für einen Geist von edlen Anlagen.\nGottfried Keller (1819-1890)",
			"Für den Einsamen ist schon Lärm ein Trost.\nFriedrich Nietzsche (1844-1900)",
			"Ich habe nie eine Gesellschaft gefunden, die so gesellig war, wie die Einsamkeit.\nHenry David Thoreau (1817-1862)",
			"Mancher Mensch hat ein großes Feuer in seiner Seele, und niemand kommt, um sich daran zu wärmen.\nVincent van Gogh (1853-1890)",
			"Niemals bin ich allein. Viele, die vor mir lebten und fort von mir strebten, webten, webten an meinem Sein.\nRainer Maria Rilke (1875-1926)",
			"Sei artig und du wirst einsam sein.\nMark Twain (1835-1910)",
			"überlege wohl, bevor du dich der Einsamkeit ergibst, ob du auch für dich selbst ein heilsamer Umgang bist.\nMarie von Ebner-Eschenbach (1830-1916)",
			"Was ich geleistet habe, ist nur ein Erfolg des Alleinseins.\nFranz Kafka (1883-1924)",
			"Wenn du Einsamkeit nicht ertragen kannst, dann langweilst du vielleicht auch andere.\nOscar Wilde (1854-1900)",
			"Wer sich der Einsamkeit ergibt, ach, der ist bald allein!\nJohann Wolfgang von Goethe (1749-1832)",
			"Wir weinen immer allein.\nFanny zu Reventlow (1871-1918)",
			"Wer einsam ist, der hat es gut, weil keiner da, der ihm was tut. (Wilhelm Busch 1832-1908)",
			"Aber hier, wie überhaupt, kommt es anders, als man glaubt.\nWilhelm Busch (1832-1908)",
			"Alle Enttäuschungen sind gering im Vergleich zu denen, die wir an uns selbst erleben.\nMarie von Ebner-Eschenbach (1830-1916)",
			"Das Undankbarste, weil Unklügste, was es gibt, ist Dank zu erwarten oder zu verlangen!\nTheodor Fontane (1819-1898)",
			"Denn solange du glücklich, wirst viele Freunde du zählen;\nwenn sich dein Himmel bewölkt, findest du dich allein.\nOvid (um Christi Geburt)",
			"Der Himmel versperrt nicht jeden Weg.\nFernöstliche Weisheit",
			"Der höhere Mensch ist mehr verbittert, wenn ihn die Menschen nicht beachten.\nFernöstliche Weisheit",
			"Die Erfahrung lässt sich ein furchtbar hohes Schulgeld bezahlen, doch sie lehrt wie niemand sonst!\nThomas Carlyle (1795-1881)",
			"Du fühlst die bittere Enttäuschung, wenn du Güte anbietest und sie dir mit Unrecht vergolten wird.\nPlato (um 400 v.Chr.)",
			"Einen ewigen Fehler machen all jene Menschen, die sich unter Glückseligkeit die Erfüllung ihrer Wünsche vorstellen.\nLeo Tostoi (1828-1910)",
			"Enttäuschungen sollte man verbrennen, nicht einbalsamieren.\nMark Twain (1835-1910)",
			"Es gibt kaum eine größere Enttäuschung, als wenn du mit einer recht großen Freude im Herzen zu gleichgültigen Menschen kommst.\nChristian Morgenstern (1871-1914)",
			"Es wäre verlorene Mühe, wollte man den Mond aus dem Wasser fischen.\nFernöstliche Weisheit",
			"Gegen das Fehlschlagen eines Planes gibt es keinen besseren Trost, als auf der Stelle einen neuen zu machen oder bereitzuhalten.\nJean Paul (1763-1825)",
			"Habe Hoffnungen, aber habe niemals Erwartungen. Dann erlebst du vielleicht Wunder, aber niemals Enttäuschungen.\nFranz von Assisi (1182-1226)",
			"In böser Sach' ein guter Mut, macht, dass nicht halb so weh' es tut.\nJohann Fischart (1545-1591)",
			"Enttäuschungen sind die Amme der Weisheit.\nBoyle Roche (1743-1807)",
			"Man erlebt immer wieder Enttäuschungen, aber man lernt auch immer besser damit umzugehen.\nDonatien Alphonse Francois de Sade (1740-1814)",
			"Man muss lernen, Schmerz und Enttäuschung als Teil des Lebens zu akzeptieren.",
			"Sag nie bestimmt: Es wird erreicht!\nEin hübsches Wörtchen ist 'vielleicht'.\nWilhelm Busch (1832-1908)",
			"Schaffen, das ist die große Erlösung vom Leiden.\nFriedrich Nietzsche (1844-1900)",
			"Schlimmer betrogen ist, wer aus Angst und Enttäuschung immer wieder sein Glück versäumte, als wer jede Möglichkeit eines Glückes ergriff, selbst auf die Gefahr hin, es könnte wieder nicht das Wahre gewesen sein.\nArthur Schnitzler (1862-1931)",
			"Selbsterkenntnis gibt dem Menschen das meiste Gute,\nSelbsttäuschung aber das meiste Übel.\nSokrates (um 450 v.Chr.)",
			"Taten lehren den Menschen und Taten trösten ihn - fort mit den Worten.\nHeinrich Pestalozzi (1746-1827)",
			"'Und ich habe mich so gefreut!' sagst du vorwurfsvoll, wenn dir eine Hoffnung zerstört wurde. Du hast dich gefreut, ist das nichts?\nMarie von Ebner-Eschenbach (1830-1916)",
			"Unsere Zeit ist eine Zeit der Erfüllung, und Erfüllungen sind immer Enttäuschungen.\nRobert Musil (1880-1942)",
			"Vergiss Kränkungen, aber vergiss Freundlichkeiten nie.\nAsiatische Weisheit",
			"Vergiss die alten Hoffnungen, wirf dein vergangenes Leben weg, entschließe dich ein neues anzufangen.\nFriedrich Schiller (1759-1805)",
			"Wenn sich eine Tür schließt, so öffnet sich eine andere.\nAlexander Graham Bell (1847-1922)",
			"Wer längst Vergangenes in der Gegenwart aufsuchen möchte, setzt sich meist einer großen Enttäuschung aus.\nWilhelm Busch (1832-1908)",
			"Wer sich auf andere verlässt, der ist verlassen.\nSprichwort",
			"Wo immer man sich auf das Leben einlässt, wird man enttäuscht. Alles dauert entweder zu lange oder nicht lange genug.\nOscar Wilde (1854-1900)",
			"Aller Anfang ist leicht, und die letzten Stufen werden am seltensten erstiegen.\nJohann Wolfgang von Goethe (1749-1832)",
			"Andauernd heißt es, der und der steht mir im Weg. Aber wenn man genau nachsieht, stehen wir auch dem und jenem im Weg!\nMaxim Gorki (1868-1936)",
			"Armselig der Schüler, der seinen Meister nicht übertrifft.\nLeonardo da Vinci (1452-1519)",
			"Auf die Absicht aller Dinge, nicht auf den Erfolg blickt der Weise.\nSeneca (um Christi Geburt)",
			"Bedenke, ein Stück des Weges liegt hinter dir, ein anderes Stück hast du noch vor dir. Wenn du verweilst, dann nur, um dich zu störken, aber nicht um aufzugeben.\nAugustinus von Hippo (354-430)",
			"Bereit sein ist viel, warten können ist mehr, doch erst den rechten Augenblick nützen ist alles.\nArthur Schnitzler (1862-1931)",
			"Wenn kein Wind geht, dann rudere.", "Jeder fängt mal klein an.\nSprichwort",
			"Das Geheimnis des Erfolges liegt darin, für die Gelegenheit bereit zu sein, wenn sie kommt.\nBenjamin Disraeli (1804-1881)",
			"Dem Neid wirst du entgehen, wenn du verstehst, dich im Stillen zu freuen.\nKonfuzius (551-479 v.Chr.)",
			"Die Anzahl unserer Neider bestätigen unsere Fähigkeiten.\nOscar Wilde (1854-1900)",
			"Die Ernte kann nur so ertragreich sein, wie der Boden fruchtbar ist, auf den die Saat fällt.\nKlemens Oberforster",
			"Eine Idee muss Wirklichkeit werden können, sonst ist sie nur eine Seifenblase.\nBerthold Auerbach (1812-1882)",
			"Erfolg hat drei Buchstaben: TUN!\nJohann Wolfgang von Goethe (1749-1832)",
			"Erfolg hat nur, wer etwas tut, während er auf den Erfolg wartet.\nThomas Alva Edison (1847-1931)",
			"Es führen viele Wege zum Gipfel eines Berges, doch die Aussicht bleibt gleich.\nChinesische Weisheit",
			"Erfolgsregel: Jage nie zwei Hasen auf einmal.\nOtto von Bismarck (1815-1898)",
			"Es genügt nicht, nur fleißig zu sein, das sind die Ameisen auch.\nDie Frage ist vielmehr: Wofür sind wir fleißig?\nHenry David Thoreau (1817-1862)",
			"Es gibt Menschen, die Fische fangen und solche, die nur das Wasser trüben.\nChinesische Weisheit",
			"Es gilt Wurzeln zu schlagen, wenn die Erde fruchtbar ist.\nKlemens Oberforster",
			"Es kann dir jemand die Tür öffnen, hindurchgehen musst du selbst.\nKonfuzius (um 500 v.Chr.)",
			"Es liegt im Wesen des Genies, die einfachsten Ideen auszunutzen.\nCharles Pierre Peguy (1873-1914)",
			"Finde heraus, wo deine störksten Wurzeln liegen, und verlange nicht nach anderen Welten.\nHenry David Thoreau (1817-1862)",
			"Fleiß ist die Mutter des Glücks, und den Fleißigen schenkt Gott alle Dinge.\nBenjamin Franklin (1706-1790)",
			"Fürchte dich nicht vor dem langsamen Vorwärtsgehen, fürchte dich nur vor dem Stehenbleiben.\nChinesische Weisheit",
			"Aus ungelegten Eiern schlüpfen keine Hühner.",
			"Geh nicht immer auf dem vorgezeichneten Weg, der nur dahin führt, wo andere bereits gegangen sind.\nAlexander Graham Bell (1847-1922)",
			"Gott gibt jedem Vogel seine Nahrung, wirft sie ihm aber nicht ins Nest.\nJosiah Gilbert Holland (1819-1881)",
			"Guten Advokaten fehlt es nicht an Klienten.\nWilliam Shakespeare (1564-1616)",
			"Ich verdanke meinen Erfolg weniger meinen Kenntnissen als meinem Charakter.\nRobert Bosch (1861-1942)",
			"In der Not erst magst du zeigen, wer du bist und was du kannst.\nEmanuel Geibel (1815-1884)",
			"Jeder Künstler war anfangs ein Amateur.\nRalph Waldo Emerson (1803-1882)",
			"Nichts auf der Welt ist so stark wie eine Idee, deren Zeit gekommen ist.\nVictor Hugo (1802-1885)",
			"Nichts Großes ist jemals ohne Begeisterung vollbracht worden.\nRalph Waldo Emerson (1803-1882)",
			"Nicht, was ich habe, sondern was ich schaffe, ist mein Reich.\nThomas Carlyle (1795-1881)",
			"Nur auf dem Pfad der Nacht erreicht man die Morgenröte.\nKhalil Gibran (1883-1931)",
			"Sehr einfache Ideen liegen nur in Reichweite der kompliziertesten Gehirne.\nRemy de Gourmont (1858-1915)",
			"Suche nicht andere, sondern dich selbst zu übertreffen.\nMarcus Tullius Cicero (106-43 v.Chr.)",
			"Tüchtiges Schaffen, das hält auf die Dauer kein Gegner aus.\nPeter Rosegger (1843-1918)",
			"Tüchtigkeit, nicht Geburt unterscheidet die Menschen.\nVoltaire (1694-1778)",
			"Unterschätze nie einen Menschen, der einen Schritt zurück macht. Er könnte Anlauf nehmen!",
			"Unterschütz mich ruhig, umso lustiger wird es für mich!",
			"Vitamin P (Protektion) hilft immer.\nSprichwort",
			"Wandel entsteht aus dem Einwirken einer neuen Idee auf einen aufnahmebereiten Geist.\nLouis Pasteur (1822-1895)",
			"Was kann man aber nicht alles, wenn man sich einbildet, vollendet zu sein, und kühn weiterstrebt?\nDietrich Christian Grabbe (1801-1836)",
			"Was wir am nötigsten brauchen, ist ein Mensch, der uns zwingt, das zu tun, was wir können.\nRalph Waldo Emerson (1803-1882)",
			"Wege entstehen dadurch, dass man sie geht.\nFranz Kafka (1883-1924)",
			"Wer denkt, wird nie fliegen.\nAnatole France (1844-1924)",
			"Wer der Herde folgt, läuft immer den Ärschen hinterher!",
			"Wer eine neue Idee hat, ist ein Spinner, bis die Idee einschlägt.\nMark Twain (1835-1910)",
			"Wer immer nur in die Fußstapfen anderer tritt, hinterlässt keine eigenen Spuren.\nWilhelm Busch (1832-1908)",
			"Wer mit Leidenschaft und dazu noch effizienzorientiert arbeitet, bei dem stellt sich der Erfolg von alleine ein.\nKlemens Oberforster",
			"Der Weg des Erfolges hat meist viele Stufen.",
			"Alle Dinge werden zu einer Quelle der Lust, wenn man sie liebt.\nThomas von Aquin (1224-1274)",
			"Am Ende gilt doch nur, was wir getan und gelebt, und nicht was wir ersehnt haben.\nArthur Schnitzler (1862-1931)",
			"Beginne jeden Tag als wäre es Absicht.\nJean-Jacques Rousseau (1712-1778)",
			"Das ist eine Hauptaufgabe des Lebens: Die physische Jugend welche vorübergeht, durch eine geistige zu ersetzen.\nRudolf Eucken (1846-1926)",
			"Das Leben an einem Ort ist erst dann schön, wenn die Menschen ein gutes Verhältnis zu einander haben.\nKonfuzius (551-479 v.Chr.)",
			"Der Kultivierte bedauert nie einen Genuss. Der Unkultivierte weiß überhaupt nicht, was ein Genuss ist.\nOscar Wilde (1854-1900)",
			"Der Tag ist verloren, ausgebrochen aus der Kette deines Lebens, den du in Trübsinn und tatenloser Verzweiflung hinstarrst.\nBerthold Auerbach (1812-1882)",
			"Es kommt nicht darauf an, dem Leben mehr Jahre zu geben, sondern den Jahren mehr Leben zu geben.\nAlexis Carrel (1873-1944)",
			"Es liegt auf der Hand, wie töricht der Glaube ist, dass uns das Leben um des Genusses willen gegeben sei.\nLeo Tolstoi (1828-1910)",
			"Für beides danken, für das, was wir haben, und für das, was wir nicht brauchen.\nJosef Geyer (1862-1929)",
			" Schau in die Welt mit wachen Sinnen, wirst immer Neues ihr abgewinnen.\nOskar Blumenthal (1852-1917)",
			"Unser Leben ist arm und taugt nicht viel, wenn wir es bequem haben unter den Menschen.\nChristian Geyer (1862-1929)",
			"Was wäre das Leben, hätten wir nicht den Mut, etwas zu riskieren?\nVincent van Gogh (1853-1890)",
			"Wenn du das Leben liebst, dann vergeude keine Zeit, denn daraus besteht das Leben.\nBenjamin Franklin (1706-1790)",
			"Wer jeden Abend sagen kann: 'Ich habe gelebt.', dem bringt jeder Morgen einen neuen Gewinn.\nSeneca (um Christi Geburt)",
			"Zu leben, ohne zu lieben, ist kein richtiges Leben. Nimm' dem Leben die Liebe, und du nimmst ihm sein Vergnügen.\nMolière (1622-1673)",
			"Zwei Dinge bedeuten mir Leben: Die Freiheit und die Frau, die ich liebe.\nVoltaire (1694-1778)",
			"Auf seine Freiheit verzichten heißt auf seine Würde als Mensch, auf die Menschenrechte, ja sogar auf seine Pflichten verzichten.\nJean-Jacques Rousseau (1712-1778)",
			"Der Mensch ist frei geboren, und überall liegt er in Ketten.\nJean-Jacques Rousseau (1712-1778)",
			"Die Fähigkeit, das Wort 'Nein' auszusprechen, ist der erste Schritt zur Freiheit.\nNicolas Chamfort (1741-1794)",
			"Freiheit muss man sich oft hart erarbeiten",
			"Die Freiheit des Menschen liegt nicht darin, dass er tun kann, was er will, sondern das er nicht tun muss, was er nicht will.\nJean-Jacques Rousseau (1712-1778)",
			"Die Gedankenfreiheit ist die einzig wahre und die größte Freiheit, die der Mensch erreichen kann.\nMaxim Gorki (1868-1936)",
			"Die glücklichen Sklaven sind die erbittertsten Feinde der Freiheit.\nMarie von Ebner-Eschenbach (1830-1916)",
			"Die Welt hat nie eine gute Definition für das Wort Freiheit gefunden.\nAbraham Lincoln (1809-1865)",
			"Freiheit ist immer die Freiheit des Andersdenkenden.\nRosa Luxemburg (1870-1919)",
			"Freiheit kann man einem zwar lassen, aber nicht geben.\nFriedrich Schiller (1759-1805)",
			"Ich mag verdammen, was du sagst, aber ich werde mein Leben dafür einsetzen, dass du es sagen darfst.\nVoltaire (1694-1778)",
			"Jede Revolution war zuerst ein Gedanke im Kopf eines Menschen.\nRalph Waldo Emerson (1803-1882)",
			"Die schönsten Träume von Freiheit werden im Kerker geträumt. (Friedrich Schiller 1759-1805)\nEuripides (um 450 v. Chr.)",
			"Man kann eine Idee durch eine andere verdrängen, nur die der Freiheit nicht.\nLudwig Börne (1786-1837)",
			"Mir ist die gefährliche Freiheit lieber als eine ruhige Knechtschaft.\nJean-Jacques Rousseau (1712-1778)",
			"Niemand ist frei, der über sich selbst nicht Herr ist.\nMatthias Claudius (1740-1815)",
			"Was nützt die Freiheit des Denkens, wenn sie nicht zur Freiheit des Handelns führt.\nJonathan Swift (1667-1745)",
			"Wer anderen die Freiheit verweigert, verdient sie nicht für sich selbst.\nAbraham Lincoln (1809-1865)",
			"Wer an die Freiheit des menschlichen Willens glaubt, hat nie geliebt und nie gehasst.\nMarie von Ebner-Eschenbach (1830-1916)",
			"Wer die Freiheit aufgibt, um Sicherheit zu gewinnen, wird am Ende beides verlieren.\nBenjamin Franklin (1706-1790)",
			"Wer nicht sein eigener Meister, ist nicht frei.\nEpiket (um 100 n.Chr.)",
			"Wer Sicherheit der Freiheit vorzieht, ist zu Recht ein Sklave.\nAristoteles (384-322 v.Chr.)",
			"Wer sich den Gesetzen nicht fügen will, muss die Gegend verlassen, wo sie gelten.\nJohann Wolfgang von Goethe (1749-1832)",
			"Wer wär nicht meist da am liebsten, wo er ungefähr denken kann, was er mag.\nWilhelm Busch (1832-1908)",
			"Anderen Menschen Gutes zu tun, ist keine Pflicht. Es ist eine Freude, denn damit wächst unsere eigene Gesundheit und Glückseligkeit.\nZarathustra (lebte vor Christus)",
			"Das Lächeln, das du jemandem schenkst, kehrt zu dir zurück.\nIndisches Sprichwort",
			"Der ist fürwahr ein armer Mann, der nicht auch frühlich feiern kann.\nFriedrich Rückert (1788-1866)",
			"Der verlorenste aller Tage ist der, an dem man nicht gelacht hat.\nNicolas-Sebastien Chamfort (1741-1794)",
			"Die beste Freude ist wohnen in sich selbst.\nJohann Wolfgang von Goethe (1749-1832)",
			"Die schönste Freude erlebt man immer da, wo man sie am wenigsten erwartet hat.\nAntoine de Saint-Exupery (1900-1944)",
			"Die Seele ernährt sich von dem, worüber sie sich freut.\nAugustinus von Hippo (354-430)",
			"Er ist so freudenvoll, dass ihm der Stöpsel aus der Seele fliegt.\nWilhelm Busch (1832-1908)",
			"Freude ist ein Zeichen, dass man dem Licht nahe ist.\nEdith Stein (1891-1942)",
			"Freude ist keine Gabe des Geistes, sie ist eine Gabe des Herzens.\nLudwig Börne (1786-1837)",
			"Frühliche Menschen sind nicht bloß glücklich, sondern in der Regel auch gute Menschen.\nKarl Julius Weber (1767-1832)",
			"Frühlichkeit ist nicht die Flucht vor der Traurigkeit, sondern der Sieg über sie.\nGorch Fock (1880-1916)",
			"Geduld ist der Schlüssel zur Freude.\nSprichwort",
			"In jede hohe Freude mischt sich eine Empfindung der Dankbarkeit.\nMarie von Ebner Eschenbach (1830-1916)",
			"Mach andern Freude! Du wirst erfahren, dass Freude freut.\nFriedrich Theodor Vischer (1807-1887)",
			"Mit Kummer kann man allein fertig werden, aber um sich aus vollem Herzen freuen zu können, muss man die Freude teilen.\nMark Twain (1835-1910)",
			"Was kann der Schöpfer lieber sehen, als ein frühliches Geschöpf.\nGotthold Ephraim Lessing (1729-1781)",
			"Wer keine Freude an der Welt hat, an dem hat die Welt auch keine Freude.\nBerthold Auerbach (1812-1882)",
			"Wir streben mehr danach, Schmerz zu vermeiden als Freude zu gewinnen.\nSigmund Freud (1956-1939)",
			"Zum Mitleiden gehört nur ein Mensch, zum Mitfreuen ein Engel.\nJean Paul (1763-1825)",
			"Alte Knaben haben genauso ihr Spielzeug wie die jungen, der Unterschied liegt lediglich im Preis.\nBenjamin Franklin (1706-1790)",
			"Das Alter ist nicht trübe, weil darin unsere Freuden, sondern weil unsere Hoffnungen aufhören.\nJean Paul (1763 -1825)",
			"Das muss ein Esel sein, der mit sechzig noch die gleiche Meinung hat wie mit dreißig!\nOtto von Bismarck (1815-1898)",
			"Dass der Verstand erst mit den Jahren kommt, sieht man nicht eher ein, als bis der Verstand und die Jahre da sind.\nJean Paul (1763 -1825)",
			"Das Greisenalter, das alle zu erreichen wünschen, klagen alle an, wenn sie es erreicht haben.\nMarcus Tullius Cicero (106-43 v.Chr.)",
			"Das Menschenleben ist seltsam eingerichtet: Nach den Jahren der Last hat man die Last der Jahre.\nJohann Wolfgang von Goethe (1749-1832)",
			"Dem grauen Scheitel fällt das Lernen schwer.\nEmanuel Geibel (1815-1884)",
			"Die Kindheit ist der Abschnitt des Lebens zwischen dem Schwachsinn des Säuglings und der Torheit der Jugend, nur zwei Schritte entfernt von der Sünde des Erwachsenenlebens und drei von der Reumütigkeit des Alters.\nAmbrose Bierce (1842-1914)",
			"Gold und Lachen können das Alter zur Jugend machen.\nTalmud",
			"Habgier im Alter ist eine Narrheit. Vergrößert man denn seinen Reiseproviant, wenn man sich dem Ziel nähert?\nMarcus Tullius Cicero (106-43 v.Chr.)",
			"Die Menschen werden alt, aber selten reif.\nAlphonse Daudet (1840-1897)",
			"Im Alter ist Unabhängigkeit eine Form der Sklaverei.\nGiacomo Casanova (1725-1798)",
			"Je älter man wird, desto mehr findet sich Ersatz der natürlichen Liebenswürdigkeit durch künstliche.\nWilhelm Busch (1832-1908)",
			"Jeder, der sich die Fähigkeit erhält Schönes zu erkennen, wird nie alt werden.\nFranz Kafka (1883-1924)",
			"Jeder möchte lange leben, aber keiner will alt werden.\nJonathan Swift (1667-1745)",
			"Jedes Alter hat seine Vergnügungen, seinen Geist und seine Sitten.\nNicolas Boileau (1636-1711)",
			"Mit dem Alter nimmt die Urteilskraft zu und das Genie ab.\nImmanuel Kant (1724-1804)",
			"Mit zwanzig regiert der Wille, mit dreißig der Verstand und mit vierzig das Urteilsvermögen.\nBenjamin Franklin (1706-1790)",
			"Nicht die Jahre, sondern die Untätigkeit macht uns alt.\nChristine von Schweden (1626-1689)",
			"Nichts gewinnt so sehr durch das Alter wie Brennholz, Wein, Freundschaften und Bücher.\nFrancis Bacon (1561-1626)",
			"Um seine Jugend zurückzubekommen, braucht man nur seine Torheiten zu wiederholen.\nOscar Wilde (1854-1900)",
			"Welche Freude, wenn es heißt: Alter, du bist weiß an Haaren, blühend aber ist dein Geist.\nGotthold Ephraim Lessing (1729-1781)",
			"Wenn wir alt werden, so beginnen wir zu disputieren, wollen klug sein und doch sind wir die größten Narren.\nMartin Luther (1483-1546)",
			"Wenn wir alt werden, verkriecht sich die Schönheit nach innen.\nRalph Waldo Emerson (1803-1882)",
			"Wer mit 19 kein Revolutionär ist, hat kein Herz. Wer mit 40 immer noch ein Revolutionär ist, hat keinen Verstand.\nTheodor Fontane (1819-1898)",
			"Man ist nur so alt, wie man sich fühlt.\n(Sprichwort)",
			"Alkohol am Arbeitsplatz schafft so manchen wirren Satz.",
			"Arbeit ist das beste Mittel gegen Trauer.\nArthur Conan Doyle (1859-1930)",
			"Arbeite klug, nicht hart.\nSprichwort",
			"Die Bedürfnisse eines einzelnen werden durch die Arbeit von Hunderten befriedigt.\nChinesisches Sprichwort",
			"Ein guter Abend kommt heran, wenn ich den ganzen Tag getan.\nJohann Wolfgang von Goethe (1749-1832)",
			"Eine Stunde konzentrierter Arbeit facht die Lebensfreude besser an als ein Monat dumpfen Brötens.\nBenjamin Franklin (1706-1790)",
			"Handwerk hat goldenen Boden.\nalte Weisheit",
			"Lasst uns arbeiten, ohne zu grübeln. Das ist das einzige Mittel, das Leben erträglicher zu machen.\nVoltaire (1694-1778)",
			"Wie gut anständ'ger Scherz der Arbeit ziemt.\nWilliam Shakespeare (1564-1616)",
			"Zwischen Reden und Tun liegt das Meer.\nitalienisches Sprichwort",
			" Wenn die Bäume waagrecht wachsen, hat der Fürster nichts zu lachen.",
			"Alle Köche sind beschissen, die sich nicht zu helfen wissen.",
			"Arbeit hat mit Nichtstun überhaupt nichts zu tun.",
			"Der Schreiner arbeitet auf 1mm genau, der Zimmermann auf 1cm, und der Maurer muss aufpassen am gleichen Grundstück zu bleiben.",
			"Es ist unglaublich, welche Arbeiten der Mensch auf sich nimmt, um nicht arbeiten zu müssen.\nMiguel de Unamuno (1864-1936)",
			"In unserem Geschäft ist alles elektrisch. Sogar beim Gehalt trifft einen der Schlag!",
			"Kannst du Karren schieben, kannst du Arbeit kriegen.",
			"Kaum arbeiten mal zwei Hand in Hand, spricht man schon von Bestechung.",
			"Lieber ein Fachidiot als ein Vollidiot!", "Mich kann man nicht kündigen. Sklaven werden verkauft!",
			"Wenn einer lacht, lach mit! Wenn einer singt, sing mit! Und wenn einer arbeitet, lass ihn spinnen!",
			"An dem Dach der Eiszapf tropft, der Frühling an die Scheune klopft.",
			"Auf dem Heu und in dem Stroh, ward schon manches Mägdlein froh.",
			"Bauer Franz liegt auf der Wiese, unter ihm die Magd Luise.",
			"Baut der Knecht beim Ernten Scheiß, bekommt er vom Bauern Feldverweis!",
			"Beim Dreschen lass das Rauchen sein, sonst atmest du den Brandrauch ein.",
			"Blitzt und donnert es mit Schauern, kriecht das Vieh ins Bett zum Bauern!",
			"Cremen sich die Schweine ein, wird's ein heißer Sommer sein.",
			"Der Bauer macht ein Bäuerlein, es muss nicht mit der Bäuerin sein!",
			"Der Bauer schlägt, man glaubt es kaum, zum Fest der Liebe einen Baum.",
			"Der Bauer wird sich hüten, die Eier selbst zu brüten.",
			"Der Hofhund, der die Hühner frisst, ein hundsgemeines Haustier ist.",
			"Der Traktor seinen Bauern foppt, wenn er zu spät vorm Dorfteich stoppt.",
			"Die Bäuerin raubt dem Knecht die Ruh, schlüpft sie zum Melken in's Dessous.",
			"Droht der Bauer mit der Rute, zieht die Stute eine Schnute!",
			"Es lässt den Bauern gar nicht ruh'n, wenn die Hähne morgens muh'n.",
			"Fährt der Bauer raus zum Jauchen, wird er nachts ein Deo brauchen.",
			"Fällt der Bauer auf sein' Stängel, wird's nichts mit dem nächsten Bengel!",
			"Fällt der Bauer von der Leiter, find' der Ochs dies äußerst heiter!",
			"Fällt der Bauer tot vom Traktor, glüht am Waldrand der Reaktor!",
			"Fällt der Bauer voll vom Trecker, war der Korn mal wieder lecker.",
			"Fällt der Baum beim ersten Streich, war bestimmt der Baumstamm weich.",
			"Fällt der Bauer in den Dünger, wird er davon auch nicht jünger.",
			"Fällt der Pfarrer in den Mist, lacht der Bauer bis er pisst!",
			"Fällt der Knecht plötzlich vom Traktor war kaputt wohl ein Reaktor.",
			"Fehlt der Knecht am Morgen ständig, war die Magd nachts sehr lebendig.",
			"Fliegt der Bauer übers Dach, ist der Wind weiß Gott nicht schwach!",
			"Fummeln Magd und Knecht im Stroh, brüllt der Ochse: 'Pornoshow'!",
			"Geht die Bäuerin in die Breite, sucht der Bauer schnell das Weite.",
			"Haben die Kühe nichts zu fressen, hat sie der Bauer wohl vergessen.",
			"Hat der Bauer Bock auf Schinken, fängt der Eber an zu hinken!",
			"Hat der Bauer kalte Ohren, hat er seinen Hut verloren!",
			"Hat der Bauer kalte Schuhe, steht er in der Tiefkühltruhe.",
			"Hat der Melker kalte Finger, wird die Kuh zum Stabhochspringer.",
			"Hat die Magd 'nen wunden Po, lag die Egge unterm Stroh.",
			"Hebt ein Tier am Baum ein Bein, weiß man gleich, das ist kein Schwein.",
			"Hülsenfrucht zum Abendbrot, morgens sind die Fliegen tot!",
			"Hüpft der Ochse auf dem Schwan, handelt sich's um Rinderwahn.",
			"Isst der Bauer Stoppelrüben, kommt die Blähung dann in Schüben!",
			"Ist der Bauer am Verrecken, wird er wohl im Silo stecken.",
			"Ist des Bauern Gras nur braun, muss er wohl ein Plumpsklo bau'n.",
			"Ist dem Bauern kühl am Schuh, steht er in der Tiefkühltruh'!",
			"Ist der Bauer heut' gestorben, braucht er nichts zu essen morgen.",
			"Ist der Bauer noch nicht satt, fährt er sich ein Hähnchen platt.",
			"Ist der Bauer voll mit Bieren, geht's nach Haus auf allen vieren.",
			"Ist der Bauer völlig blank, gehört der Hof wohl bald der Bank.",
			"Ist der Hahn erkältet heiser, kräht er morgens etwas leiser.",
			"Ist die Viehzucht aufgegeben, heißt es von Touristen leben.",
			"Kann der Bauer nicht mehr laufen, war er vorher wieder saufen.",
			"Kippt der Bauer Milch in'n Tank, wird der Trecker sterbenskrank!",
			"Klebt der Bauer an der Mauer, war der Stier wohl richtig sauer!",
			"Kocht die Bäuerin faule Eier, kotzt der Bauer wie ein Reiher!",
			"Kommt der Gockel untern Trecker, gibt es morgen keinen Wecker!",
			"Kotzt der Bauer in das Heu, stinkt es bald wie Katzenstreu.",
			"Kotzt der Bauer über'n Trecker, war die Brotzeit nicht sehr lecker!",
			"Kräht der Bauer auf dem Mist, hat sich wohl der Hahn verpisst!",
			"Kräht der Bauer auf dem Mist, weißt du, dass er besoffen ist!",
			"Kräht der Maulwurf auf dem Dach, liegt der Hahn vor Lachen flach.",
			"Lässt der Bauer einen fahren, flieht das Vieh in hellen Scharen!",
			"Lässt zu früh der Hahn das Krähen sein, haut ihm der Bauer eine rein!",
			"Liegt der Bauer auf der Lauer, ist Herr Lauer äußerst sauer!",
			"Liegt der Bauer unterm Tisch, war das Essen nimmer frisch!",
			"Liegt der Bauer tot im Zimmer, lebt er nimmer.\nLiegt die Bäuerin tot daneben, ist sie auch nicht mehr am Leben.\nSind die Kinder auch noch dort, war es wohl ein Massenmord.",
			"Liegt der Bauer grün im Schrank, ist er scheinbar krank.",
			"Liegt der Bauer unterm Tisch, war das Essen nimmer frisch!",
			"Liegt des Bauern Uhr im Mist, weiß er nicht wie spät es ist.",
			"Mai und Knecht und nasses Gras, die Bäuerin kriegt`s am Ischias.",
			"Melkt der Bauer statt der Kuh die Sau, ist er mal wieder restlos blau.",
			"Melkt die Bäuerin die Kühe, hat der Bauer keine Mühe.",
			"Mischt der Bauer Gift zur Butter, ist sie für die Schwiegermutter!",
			"Muht die Kuh laut im Getreide, war ein Loch im Zaun der Weide.",
			"Nimmt die Magd die Eier fort, schrein die Hühner: 'Kindermord!'",
			"Nur ein Held, fährt auf's Feld.",
			"Pflanzt der Bauer Dope ins Feld, verdient er nebenbei viel Geld!",
			"Pflanzt der Bauer Öko-Beete, denkt er kichernd an die Knete!",
			"Raucht der Bauer im Stall sein Hasch, fragt die Kuh, wasch ischn dasch?",
			"Raucht die Kuh wie ein Kamin, ist Kurzschluss in der Melkmaschin'!",
			"Rülpst im Stalle laut der Knecht, wird sogar den Säuen schlecht.",
			"Scharren sich die Bauern ein, wird's ein harter Winter sein.",
			"Schlägt der Blitz den Bauer tot, spart sein Weib ein Abendbrot.",
			"Schmeißt der Knecht die Hose hin, liegt die Magd im Heu schon drin.",
			"Schweinkram mit der Sau im Bette, hat der Bauer auf Kassette!",
			"Sieht die Magd den Bauern nackt, wird vom Brechreiz sie gepackt!",
			"Sind die Hühner platt wie Teller, war der Traktor sicher schneller.",
			"Sind die Kühe am Verrecken, kriegt der Bauer einen Schrecken.",
			"Soll die Kälbermast sich lohnen, greift der Bauer zu Hormonen.",
			"Spielt der Bauer abends Karten, muss die Bäurin halt noch warten!",
			"Spielt der Knecht im Stall Viola, gibt die Kuh beim Melken Cola!",
			"Steht im Jänner noch das Korn, ist es wohl vergessen worn.",
			"Steht im Winter noch das Korn, ist es wohl vergessen wor'n.",
			"Steppt wild die Sau, charmant, graziös, macht das den Metzger ganz nervös.",
			"Stinkt's im Stall zu sehr nach Mist, schleicht der Ochs' sich fort mit List!",
			"Stirbt der Bauer schon im Mai, wird ein Fremdenzimmer frei!",
			"Trägt der Knecht beim Melken Strapse, muss er schnellstens in die Klapse.",
			"Trinkt der Bauer morgens Rum, werden alle Furchen krumm.",
			"Trinkt der Bauer und fährt Traktor, wird er zum Gefahrenfaktor!",
			"Trinkt der Bauer zuviel Bier, melkt der Trottel seinen Stier!",
			"Verliert der Bauer seine Hose, war bestimmt der Gummi lose.",
			"Waren die Eier plötzlich eckig, ging's den Hühnern ganz schön dreckig.",
			"Was zu Silvester ausgesät, im September lauthals kräht.",
			"Wenn der Bauer in die Jauche fliegt, der Ochse sich vor Lachen biegt!",
			"Wenn der Bauer nackend tanzt, sich die Magd im Stall verschanzt.",
			"Wenn der Knecht wie Elvis singt, die Kuh im Takt ihr Euter schwingt!",
			"Wenn der Knecht zum Waldrand wetzt, ist das Örtchen schon besetzt!",
			"Wenn die Frau Migräne plagt, steigt der Bauer gern zur Magd.",
			"Wenn die Kuh am Himmel schwirrt, hat sich die Natur geirrt.",
			"Wenn die Kühe morgens muhn, hat der Bauer viel zu tun.",
			"Wenn die Milch nach Krypton schmeckt, hat's im Kernkraftwerk geleckt!",
			"Wenn es draußen windet und wettert, der Bauer auf die Bäuerin klettert.",
			"Wenn es nachts im Bette kracht, der Bauer seinen Erben macht!",
			"Wenn sich das Jahr dem Ende neigt, der Bauer in die Wanne steigt.",
			"Wenn überm Acker sich die Sonne rötet, der Landmann schnell die Magd verlötet!",
			"Wird der Knecht gehetzt von Doggen, muss er um sein Leben joggen!",
			"Wollen Ochs und Kühe rennen, wird der ganze Stall wohl brennen.",
			"Würde die Sau den Teppich schonen, könnt der Bauer schöner wohnen.",
			"Zu tief in die Jauche schaun', macht den Bauern sportlich braun.",
			"Alte Füchse gehen schwer in die Falle.", "Auch an kleinen Brunnen löscht man seinen Durst.",
			"Das beste Wappen in der Welt, ist der Pflug im Ackerfeld.",
			"Der Alten Rat, der Jungen Tat, macht Krummes grad.",
			"Der Bauer macht aus Ferkeln Säue, so was nennt man Bauernschläue.",
			"Der frühe Vogel fängt den Wurm!", "Eine Kuh macht muh. Viele Kühe machen Mühe.",
			"Eine Ziege und ein Madel kosten mehr als eine Kuh im Stadel.",
			"Hüte dich, dass du den Leuten nicht in den Mund kommst, denn du kommst selten wieder heraus.\nSprichwort",
			"Je schöner die Wirtin, desto schwerer die Zeche.", "Spare in der Zeit, dann hast du in der Not.",
			"Wer einen Bauern betrügen will, muss einen Bauern mitbringen.",
			"Wer kriecht, kann nicht stolpern.", "Wer sich zum Esel macht, dem wird aufgepackt.",
			"Wer will um die Tochter bitten, achte auf der Mutter Sitten.",
			"Wo der Wirt vor der Tür steht, da sind nicht viele Gäste.",
			"Am Neujahrstage Sonnenschein lässt das Jahr uns fruchtbar sein.",
			"Aprilwetter und Frauensinn, sind veränderlich von Anbeginn.",
			"Auf die schönsten Sommertage folgen die größten Wetter.",
			"Bauen im April die Schwalben, gibt's viel Futter, Korn und Kalben.",
			"Bläst der Wind im Februar ins Horn, bläst er im Sommer auch ins Korn.",
			"Bringt der Juli heiße Glut, gerät auch der September gut.",
			"Der Nordwind ist ein rauer Vetter, doch er bringt beständiges Wetter.",
			"Dreht mehrmals sich der Wetterhahn, so zeigt er Sturm und Regen an.",
			"Ein nasser Mai schafft Milch herbei.", "Fällt das Laub zu bald, wird der Herbst nicht kalt.",
			"Fängt der August mit Hitze an, bleibt sie lang die Schlittenbahn.",
			"Friert im November früh das Wasser, dann wird der Jänner umso nasser.",
			"Fürchte nicht den Schnee im März, darunter wohnt ein warmes Herz.",
			"Geht die Sonne feurig auf, folgen Wind und Regen drauf.",
			"Gibt's im Juni Donnerwetter, wird gewiss das Getreide fetter.",
			"Hüpfen im Februar Eichhörnchen und Finken, siehst du schon den Frühling winken.",
			"Im Juli muss vor Hitze braten, was im September soll geraten.",
			"Im November kalt und klar, wird mild und trüb der Januar.",
			"Ist am Dreikönigtag kein Winter, folgt auch keiner mehr dahinter.",
			"Ist Dreikönig hell und klar, gibt's guten Wein im neuen Jahr.",
			"Ist es grün zur Weihnachtsfeier, fällt der Schnee auf Ostereier.",
			"Gewitter ohne Regen ist ohne Segen.", "Juni kalt und nass, lässt leer Scheune und Fass.",
			"Kommt der Frost im Jänner nicht, zeigt im März er sein Gesicht.", "Mairegen bringt Segen.",
			"Nach oben schau, auf Gott vertrau, nach Wolken wird der Himmel blau.",
			"Neujahrsnacht still und klar, deutet auf ein gutes Jahr!",
			"Regen im Mai bringt Wohlstand und Heu.",
			"Regnets im Sommer kaum, bleiben die Äpfel nicht am Baum.",
			"Silvesternacht düster oder klar, zeigt an ein gutes neues Jahr",
			"Sitzt im November noch das Laub, wird der Winter hart, das glaub.",
			"So golden die Sonne im Juli strahlt, so golden sich der Weizen mahlt.",
			"Stehend Wasser im Mai, bringt die Wiesen ums Heu.",
			"Um Lichtmess sehr kalt, wird der Winter nicht alt.",
			"Wächst das Gras im Januar, wächst es schlecht das ganze Jahr.",
			"Wenn es im Februar nicht schneit, dann schneit es in der Osterzeit.",
			"Wenn im Februar die Mücken schwärmen, muss man im März die Ohren wärmen!",
			"Wenn man den Dezember soll loben, muss er frieren und toben.",
			"Wind in der Nacht am Tage Wasser macht.",
			"Zeigt der Winter bis Dreikönig selten sein grimmiges Gesicht, zeigt er es auch bis Ostern nicht.",
			"Das beste Geschenk und Wesen ist ein heiteres und frühliches Herz.\nMartin Luther (1483-1546)",
			"Ein Leben ohne Feste ist ein weiter Weg ohne Wirtshäuser.\nDemokrit (um 400 v.Chr.)",
			"Ein Kind ist eine sichtbar gewordene Liebe.\nNovalis (1772-1801)",
			"Es ist nicht wichtig schön, sondern gesund und glücklich geboren zu werden.\nSprichwort",
			"Liebe, menschlich zu beglücken, nähret sie ein edles Zwei, doch zu göttlichem Entzücken bildet sie ein köstlich Drei.\nJohann Wolfgang von Goethe (1749-1832)",
			"Nun lass es dir Einerlei sein, ob es ein Junge oder ein Mädchen ist.\nPaula Modersohn-Becker (1876-1907)",
			"Viel Liebe und Glück und nicht Beschwerden, mögen begleiten dein Leben auf Erden. ",
			"Die Seele eines Kindes ist heilig, und was vor sie gebracht wird, muss wenigstens den Wert der Reinheit haben.\nJohann Wolfgang von Goethe (1749-1832)",
			"Es ist eine eigene Sache, schon durch die Geburt auf einen erhabenen Platz in der menschlichen Gesellschaft gesetzt zu sein.\nJohann Wolfgang von Goethe (1749-1832)",
			"Geburt hat da keinen Wert, wo die Tugend fehlt.\nJean-Baptiste Moliere (1622-1673)",
			"Jeder Mensch schlägt die Vorteile der Geburt bloß deswegen so hoch an, weil sie etwas Unbestreitbares sind.\nJohann Wolfgang von Goethe (1749-1832)",
			"Jedes neugeborene Kind bringt uns Nachricht von Gott, dass er noch nicht von den Menschen enttäuscht ist.\nRabindranath Thakur (1861-1941)",
			"Man muss die Menschen bei ihrer Geburt beweinen, nicht bei ihrem Tode.\nBlaise Pascal (1623-1662)",
			"Mit der Geburt beginnt für die Eltern der Abschied.\nWeisheit",
			"Mit einer Kindheit voll Liebe kann man ein ganzes Leben lang aushalten.\nJean Paul (1763-1825)",
			"Was der Storch hat heut' gebracht, wurde vor 9 Monaten mit Liebe gemacht.",
			"Wir Neugeborenen weinen, zu betreten die große Narrenbühne.\nWilliam Shakespeare (1564-1616) ",
			"Bleib gesund und stets vergnügt, weil mir das am Herzen liegt.",
			"Die glücklichsten Tage des vergangenen Jahres mögen die schlechtesten des künftigen sein.",
			"Du denkst wohl, du bist etwas Besonderes, nur weil du heute Geburtstag hast? Du bist doch jeden Tag etwas Besonderes!",
			"Es soll das neue Lebensjahr, noch besser sein wie's alte war.",
			"Heute bin ich aufgewacht, habe an dich gedacht und da fiel mir ein, heute muss dein Geburtstag sein! Happy Birthday!",
			"Ich wünsche dir zum Wiegenfeste, von ganzem Herzen das Allerbeste und außerdem, das ist ganz klar, ein schönes neues Lebensjahr!!",
			"Ich wünsche herzlichst alles Gute: Gesundheit, Glück und Sonnenschein. Und nun mit frischem, frohem Mute ins neue Lebensjahr hinein!",
			"In deinem Alter hat man große Ziele und auch der Wünsche gibt es viele.\nDeiner inneren Stimme stets vertrau, dann wird die Zukunft himmelblau!",
			"Kummer, sei lahm! Sorge, sei blind! Es lebe das Geburtstagskind.\nTheodor Fontane (1819-1898)",
			" Man sieht mit Grauen ringsherum die Leute werden alt und dumm. Nur du und ich, auch noch als Greise, bleiben jung und werden weise.",
			"Nach einem Glückwunsch ist mir heut zumute, dem Geburtstagskind: Alles Gute!",
			"Na, das wäre ja gelacht, hätt ich heute nicht an dich gedacht.\nUm mit herzlich lieben Größen, dir deinen Geburtstag zu versüßen.",
			"Sonne, Mond und Sterne, alles ist weit in der Ferne. Doch was ich wünsche, das ist ganz nah, ein glückliches, gesundes neues Lebensjahr!",
			"Still und leise, schick ich dieses SMS auf Reise. Mit Freude und Größen, soll sie dir den Geburtstag versüßen!",
			"Tausend bunte Schmetterlinge, wünschen dir nur gute Dinge, heute ist ein besonderer Tag, an dem man Glück dir schenken mag!",
			"Voll Heiterkeit und Sonnenschein soll heute dein Geburtstag sein und außerdem sei wunderbar, das ganze neue Lebensjahr!",
			"Wenn Geburtstagsfreuden winken, kann man ruhig mal etwas trinken. Im Wein liegt Wahrheit, sagt der Brauch, drum halt dich dran und tu es auch!",
			"Den idealen Gatten gibt es nicht. Der ideale Gatte bleibt ledig.\nOscar Wilde (1954-1900)",
			"Die Hochzeit hat die Entführung nur deshalb abgelöst, weil niemand gern auf Geschenke verzichtet.\nMark Twain (1835-1910)",
			"Drum prüfe, wer sich ewig bindet, ob sich nicht was bess'res findet.",
			"Jung gefreit, lang gereut.\nSprichwort",
			"Heiraten heißt: Seine Rechte halbieren und seine Pflichten verdoppeln.\nArthur Schoppenhauer (1788-1860)",
			"Heirate oder heirate nicht, du wirst es auf jeden Fall bereuen.\nSokrates (um 450 v.Chr.)",
			"Hochzeitstag, der Tag an dem man statt zu leasen kauft.",
			"Ringlein sehn heut lieblich aus, morgen werden Fesseln draus.\nClemens Brentano (1778-1842)",
			"Sieh dir die Mutter an, bevor du dich mit der Tochter verlobst!\nSprichwort",
			"An der Frau erlebt man nicht nur die Enttäuschungen, die sie uns bereitet, sondern auch jene, die wir ihr bereiten.\nPeter Altenberg (1859-1919)",
			"Glück schleicht sich oft durch eine Tür herein, von der man gar nicht wusste, dass sie offen war!\nJohn Barrymore (1882-1942)",
			"Heirate auf jeden Fall! Wenn du eine gute Frau bekommst, wirst du glücklich. Wenn du eine schlechte Frau bekommst, wirst du Philosoph.\nSokrates (um 450 v.Chr.)",
			"Heiraten heißt für eine Frau so viel wie im Winter ins Wasser springen:\nHat sie es einmal getan, dann denkt sie ihr Lebtag daran.\nMaxim Gorki (1868-1936)",
			"Je länger man unverheiratet bleibt, desto rühmlicher ist es.\nGaius Iulius Cäsar (100-44 v.Chr.)",
			"Liebe ist etwas Ideelles, Heirat etwas Reelles, und nie verwechselt man ungestraft das Ideelle mit dem Reellen.\nJohann Wolfgang von Goethe (1749-1832)",
			"Männer heiraten, weil sie müde, Frauen, weil sie neugierig sind; beide werden enttäuscht.\nOscar Wilde (1854-1900)",
			"Raum ist in der kleinsten Hütte, für ein glücklich liebend Paar.\nFriedrich Schiller (1759-1805)",
			"Vieles auf der Welt kommt zusammen, aber selten die richtigen Paare.\nAugust Strindberg (1849-1912)",
			"Wer absolute Klarheit will, bevor er einen Entschluss fast, wird sich nie entschließen.\nHenri-Frederic Amiel (1821-1881)",
			"Wer so liebt... glaubt auch Unmögliches.\nElizabeth Barrett Browning (1806-1861)",
			"Ein Mann, der hartnäckig allein bleibt, macht sich zu einer dauernden öffentlichen Versuchung.\nOscar Wilde (1854-1900)",
			"Ich habe die Frau fürs Leben gefunden. Sie ist hübsch, macht alles mit und wenn sie mir auf den Geist geht lasse ich einfach die Luft raus.",
			"Junggeselle: Ein Mann, den die Frauen noch ausprobieren.\nAmbrose Gwinnett Bierce (1842-1914)",
			"Warum sterben verheiratete Männer vor ihren Frauen? Weil sie es wollen!",
			"Wenn du erst einmal 10 Jahre verheiratet bist, ist es egal mit wem.",
			"Wer seiner Frau alles erzählt, ist erst jung verheiratet.\nSchottisches Sprichwort",
			"Ein ganz großes Dankeschön an die liebste Mama der Welt. Genieße den Tag, denn dieser ist nur für dich. Ich hab dich ganz toll lieb.",
			"Ein Tag im Kalenderjahr gehört dir, Mutti. Dabei sollte man dich an 365 Tagen im Jahr feiern! Danke für deine Liebe und deine Mühen. Danke, dass es dich gibt!",
			"Es gibt so viele Mütter auf der Erde hier, doch eine ist die beste und die gehört mir! Alles Gute zum Muttertag!",
			"Gute Sachen sind selten, deshalb gibt es auch dich nur einmal! Alles Liebe zum Muttertag!",
			"Ich weiß genau, dass ich dich mag und das nicht nur heut am Muttertag.",
			"Liebe Mama! Nicht nur am Muttertag wollen wir an dich denken. Wir wollen dir jeden Tag unsere Liebe schenken!",
			"Waschen, bügeln, kochen, putzen, diesen Tag solltest du anders nutzen! Lass doch mal die Arbeit sein, und genieß den Tag im Sonnenschein! Alles Gute zum Muttertag!",
			"Zum Muttertag will ich dir sagen, dass ich dich mag. Will ich dir sagen, dass ich dich brauch und den Papa auch.",
			"Die Mutter liebt die Kinder mehr als der Vater,\ndenn sie weiß, dass es ihre Kinder sind.\nDieser glaubt es nur.\nMenander (um 300 v.Chr.)",
			"Der Strauß, den ich gepflücket, grüße dich viel tausendmal.\nIch habe mich oft gebücket, ach wohl ein tausendmal,\nund ihn ans Herz gedrücket, wie hunderttausendmal.\nJohann Wolfgang von Goethe (1749-1832)",
			"Alle Liebe der Menschen muss erworben, erobert und verdient, über Hindernisse hinweg erhalten werden. Die Mutterliebe allein hat man unerworben und unverdient.\nBerthold Auerbach (1812-1882)",
			"Das Beste in mir, ich hab's von ihr!\nPeter Rosegger (1843-1918)",
			"Das Herz einer Mutter ist ein Abgrund, in dessen Tiefe man immer eines findet: Bereitschaft zum Verzeihen.\nHonore de Balzac (1799-1850)",
			"Die Mütter geben unserem Geist Wärme, die Väter Licht.\nJean Paul (1763-1825)",
			"Die Natur hat das Weib unmittelbar zur Mutter bestimmt, zur Gattin bloß mittelbar, so ist der Mann umgekehrt mehr zum Gatten als zum Vater gemacht.\nJean Paul (1763-1825)",
			"Die Wohltaten der Mütter sind größer als das Meer.\nJapanisches Sprichwort",
			"Keine Weisheit, die auf Erden gelehrt werden kann, kann uns das geben, was uns ein Wort und ein Blick der Mutter gibt.\nWilhelm Raabe (1831-1910)",
			"Mutter wird manche ungesinnt, aber eine rechte Mutter sein, das ist ein schwer Ding, ist wohl die höchste Aufgabe im Menschenleben.\nJeremias Gotthelf (1797-1854)",
			"Solange wird die Liebe dauern, solang ein Mutterherz noch schlägt.\nAlbert Träger (1830-1912)",
			"Trage deine Mutter auf Armen, wenn ihr die Beine versagen, sie trug dich im Schoß, als du noch keine hattest.\nChristian Friedrich Hebbel (1813-1863)",
			"Weil Gott nicht überall sein kann, schuf er die Mutter.\nArabisches Sprichwort",
			"Das weiß ein jeder, wer's auch sei, gesund und störkend ist das Ei.\nWilhelm Busch (1832-1908)",
			"Es ist das Osterfest alljährlich, für den Hasen recht beschwerlich.\nWilhelm Busch (1832-1908)",
			"Da hüpft ein kleines Osterei nun schnell mal in dein Handy rein, wünscht dir ohne Frage, wunderschöne Ostertage!",
			"Ich wünsche dir, ganz ohne Stress, ein wunderschönes Osterfest!",
			"Jemand der dich arg vermisst, wünscht dir zu Ostern sehr, dass du froh und munter bist und viele Osternester krichst!",
			"Kommt das kleine Osterhäschen, \nstupst dich an mit seinen Näschen, \nstellt sich auf die Hinterfüße \nund sagt dir liebe Ostergrüße.",
			"Ob es regnet oder schneit, Ostern ist nicht mehr weit.",
			"Ostern bleibt ihr dies' Jahr zu Haus'?\nMacht nichts, spannt mal richtig aus!",
			"Ostern ist die Zeit des Schenkens und des aneinander denkens. Zwar kein Auto, zwar kein Nerz, dafür eine SMS mit Herz! Frohe Ostern!",
			"Ostern ist zwar noch nicht Mai. Immerhin ein paar Tage frei! Mit dem Wunsch, sie zu genießen, will ich diesen Gruß nun schließen.",
			"Wir wollen gratulieren, gerichtet ist das Haus,\nhat Fenster und hat Türen, und sieht gar stattlich aus.",
			"Der Maurer hat's gemauert, der Zimmerer überdacht;\ndoch dass es hält und dauert, das steht in Gottes Macht.",
			"Schützt auch das Dach vor Regen, die Mauer vor dem Wind,\nso ist doch allerwegen, an Gott allein gelegen, ob wir geborgen sind.",
			"Ein solches Werk kann nur geschehen, wenn jeder brav an seiner Stelle\nund alle fest zusammenstehen, der Meister, Lehrling und Geselle.",
			"Ob Kirche oder Puff, das Dach ist druff!",
			"Stolz und froh ist jeder heute, der tüchtig mit am Werk gebaut.\nEs waren wackere Handwerksleute, die fest auf ihre Kunst vertraut.",
			"Darum wünsche ich, so gut ich's kann, so kräftig wie ein Zimmermann,\nmit stolz empor gehobenem Blick dem neuen Hause recht viel Glück.",
			"Wir bitten Gott, der in Gefahren uns allezeit so treu bewahrt,\ner möge das Bauwerk hier bewahren vor Not und Schaden aller Art. ",
			" Alle guten Vorsätze haben etwas Verhängnisvolles. Sie werden beständig zu früh gefasst.\nOscar Wilde (1854-1900)",
			"Begrüsse das neue Jahr vertrauensvoll und ohne Vorurteile, dann hast du es schon halb zum Freunde gewonnen.\nNovalis (1772-1801)",
			"Das alte Jahr gar schnell entwich. Es konnt' sich kaum gedulden und ließ mit Freuden hinter sich den dicken Sack voll Schulden.\nWilhelm Busch (1832-1908)",
			"Das Fortrücken in der Kalenderjahrzahl macht wohl den Menschen, aber nicht die Menschheit reifer.\nJohann Peter Hebel (1760-1826)",
			"Das Leben gleicht einer Reise, Silvester ist ein Meilenstein.\nHeinrich Theodor Fontane (1819-1898)",
			"Das neue Jahr sieht mich freundlich an, und ich lasse das alte mit seinem Sonnenschein und Wolken ruhig hinter mir.\nJohann Wolfgang von Goethe (1749-1832)",
			"Denk im neuen Jahr daran, dass man stets alles ändern kann. Drum wünsche ich heut viel Glück und denk nicht an das schlechte Jahr zurück.",
			"Die Zeit verrinnt, die Spinne spinnt in heimlichen Geweben. Wenn heute Nacht das Jahr beginnt, beginnt ein neues Leben.\nJoachim Ringelnatz (1883-1934)",
			"Ob Schampus, Schaumwein oder Sekt, bei der richtigen Menge knickt jeder weg!",
			"Es gibt bereits alle guten Vorsätze, wir brauchen sie nur noch anzuwenden.\nBlaise Pascal (1623-1662)",
			"Es klopft ganz leise an der Tür, ein fettes rosa Rüsseltier. Mach auf der Sau, lass sie herein, so hast auch du im nächsten Jahr viel Schwein!",
			"Fege den Staub des letzten Jahres fort und mit ihm alle unguten Gefühle.\nchinesisches Sprichwort",
			"Ich wünsche dir fürs neue Jahr\ndas große Glück in kleinen Dosen.\nDas alte lässt sich ohnehin\nnicht über Nacht verstoßen.\nElli Michler (1923-2014)",
			"Sobald man davon spricht, was im nächsten Jahr geschehen wird, lacht der Teufel.\njapanisches Sprichwort",
			"Viel Glück im neuen Jahr, auf dass es nach 365 Tagen eines der besten war!\nKlemens Oberforster",
			"Wenn ein Jahr nicht leer verlaufen soll, muss man beizeiten anfangen.\nJohann Wolfgang von Goethe (1749-1832)",
			" Advent, Advent, ein Lichtlein brennt. Erst eins, dann zwei, dann drei, dann vier, dann steht das Christkind vor der Tür!",
			"Fichten, Lametta, Kugeln und Lichter, Bratäpfelduft und frohe Gesichter, Freude am Schenken,\ndas Herz wird so weit. Ich wünsch allen: Eine frühliche Weihnachtszeit!",
			"Ich wünsche frohe Feiertage, das ist doch klar und ohne Frage. Bei Tannenduft und Kerzenschein, möge alles friedlich sein!",
			"In der Geborgenheit der Familie Weihnachten zu feiern, ist in der heutigen Zeit wohl das schönste aller Geschenke!",
			"Kerzenschein und Christlaterne leuchten hell die Weihnacht ein.\nGlocken läuten nah und ferne, Friede soll auf Erden sein.",
			"Plötzchen, Stollen, Mistelzweig, bald ist wieder Weihnachtszeit.",
			"Schöne Lieder und manch warmes Wort, tiefe Sehnsucht und ein ruhiger Ort.\nGedanken, die voll Liebe klingen, Weihnachten möcht ich nur mit dir verbringen!",
			"Zeit für Liebe und Gefühl, heute bleibt es nur draußen kühl.\nKerzenschein und Apfelduft, es liegt Weihnachten in der Luft.",
			"Advent, Advent, dein Handy brennt! Erst die Anntenne, dann die Tasten und zum Schluss der ganze Kasten!",
			"Advent, Advent ein Lichtlein brennt. Erst eins, dann zwei, dann drei, dann vier und wenn das fünfte Lichtlein brennt, dann hast du Weihnachten verpennt.",
			"Der Weihnachtsbaum steht öd und leer, die Kinder schauen blöd umher, da lässt der Vater einen krachen, die Kinder fangen an zu lachen. Man sieht, man kann mit kleinen Sachen, den Kindern große Freude machen.",
			"Christkind, Christkind, guter Gast. Hast du mir was mitgebracht? Hast du was, dann setz dich nieder, hast du nichts, dann geh gleich wieder.",
			"Haben die Gäste die Geschenke gefressen, dann hat der Weihnachtsmann die Gans vergessen.",
			"Hast du zu Weihnachten schon etwas vor? Mir fehlt nämlich noch der Esel für meine Weihnachtskrippe!",
			"Hat Weihnachten das Christkind frei, dann gibt es bei der Bescherung nur Geschrei!\nKlemens Oberforster",
			"Ich hab eben das Christkind gesehn, es stand an der Bar und konnte kaum noch stehn. Auf Geschenke brauchst du nicht zu hoffen, es hat das ganze Geld versoffen!",
			"Nikolaus, komm in unser Haus, leer deine große Tasche aus,\nstell deinen Schimmel untern Tisch, dass er Heu und Hafer frisst.\nHeu und Hafer frisst er nicht? Vanillekipferl kriegt er nicht.",
			"Weihnachtszeit! Wer spricht von Siegen? überstehen ist alles.\nRainer Maria Rilke (1875-1926)",
			"Wer steht da vor der Tür und pumpert? Es ist das Christkind mit seinem Klumpert.",
			"Das Weihnachtsspielzeug der Kinder würde viel länger halten, wenn die Erwachsenen ihr eigenes bekämen.",
			"Die besinnlichen Tage zwischen Weihnachten und Neujahr haben schon manchen um die Besinnung gebracht.\nJoachim Ringelnatz (1883-1934)",
			"Ich werde Weihnachten in meinem Herzen ehren und versuchen, es das ganze Jahr hindurch aufzuheben.\nCharles Dickens (1812-1870)",
			"Kommt und seht das Wunder an. Seht was Gottes Lieb' getan.\nSeinen Sohn als kleines Kind, man in einer Krippe find't.\nMatthias Claudius (1740-1815)",
			"Und so leuchtet die Welt langsam der Weihnacht entgegen, und der in Händen sie hält, weiß um den Segen.Matthias Claudius (1740-1815)",
			"Weihnachten: Ein besonderer Tag der Völlerei, Trunksucht, Gefühlsduselei, Annahme von Geschenken, öffentlichem Stumpfsinn und häuslichem Protzen gewidmet.\nAmbrose Bierce (1842-1914)",
			"Weil das ganze Jahr über die Liebe fehlt, werden zu Weihnachten die Kinder durch Geschenke bestraft.\nHubert Ries (1802-1886)",
			"Weiß sind Türme, Dächer, Zweige, und das Jahr geht auf die Neige, und das schönste Fest ist da!\nTheodor Fontane (1819-1898)",
			"Wenn die Weihnachtsglocken läuten, wird selbst der Teufel milde.\nSprichwort",
			"Wenn je das Göttliche auf Erden erschien, so war es mit der Geburt Christi.\nJohann Wolfgang von Goethe (1749-1832)",
			"Wie leide ich vor Sehnsucht! Wäre es doch Weihnachten!\nHans Christian Andersen (1805-1875)",
			"Den eigenen Freunden zu misstrauen ist schöndlicher, als von ihnen betrogen zu werden.\nLa Rochefoucauld (1613-1680)",
			"Wenn der Himmel einen Menschen liebt, dann lässt er ihm einen Freund begegnen.\nChinesisches Sprichwort",
			"Freunde erkennst du nicht daran, wie sie dich loben, sondern daran, wie sie dich kritisieren.\nChinesische Weisheit",
			"Dein wahrer Freund ist nicht,\nwer dir den Spiegel hält der Schmeichelei,\nworin dein Bild dir selbst gefällt.\nDein wahrer Freund ist,\nwer dich sein lässt deine Flecken\nund sie dir tilgen hilft,\neh' Feinde sie entdecken.\nFriedrich Rückert (1788-1866)",
			"Dem nur blühet wahres Glück,\nDen auf seinem Pfade Freundschaft leitet.\nWas es seinen Lieblingen bereitet,\nGab dir alles das Geschick.\nEins nur ist zu geben mir geblieben\nUnd dies einzige biet ich dir an:\nEine Seele, die dich innig lieben\nUnd dir Freundschaft geben kann.\nFranz Grillparzer (1791-1872)",
			"Die Freundschaft ist die heiligste der Gaben,\nNichts Heilger's konn't uns ein Gott verleihn'.\nSie würzt die Freud' und mildert jede Pein,\nund einen Freund kann jeder haben,\nder selbst versteht, ein Freund zu sein.\nChristoph August Tiedge (1752-1841)",
			"Einen Menschen wissen,\nder dich ganz versteht,\nder in Bitternissen\nimmer zu dir steht,\nder auch deine Schwächen liebt\nweil du bist sein;\ndann mag alles brechen\ndu bist nie allein.\nMarie Ebner-Eschenbach (1830-1916)",
			"Ein Freund, der mir den Spiegel zeiget,\nden kleinsten Flecken nicht verschweiget,\nmich freundlich warnt, mich herzlich schillt,\nwenn ich nicht meine Pflicht erfüllt:\nDer ist mein Freund.\nChristian Fürchtegott Gellert (1715-1769)",
			"Ganz egal, ob groß ob klein,\njeder fühlt sich mal allein\nund ein bisschen einsam,\ndrum gehen wir gemeinsam -\nund das nenn ich Freundschaft!\nNicht wahr ist, was du denkst -\nnur was du fühlst, ist wahr;\ndurchs Denken machst du dir nur\ndas Gefühlte klar.\nFriedrich Rückert (1788-1866)",
			"Kannst du nicht mehr Geliebte sein,\nsei Freundin mir sodann;\nhat man die Liebe durchgeliebt,\nfängt man die Freundschaft an.\nHeinrich Heine (1797-1856)",
			"Weil du mich, Freund, beschenkst mit dir,\nSo dank ich billig dir mit mir.\nNimm hin deswegen mich für dich;\nIch sei dir du; sei du mir ich.\nFriedrich von Logau (1605-1655) ",
			"Das Schönste an einer Freundschaft ist nicht die ausgestreckte Hand, das freundliche Lächeln oder der menschliche Kontakt, sondern das erhebende Gefühl, jemand zu haben, der an einen glaubt\nRalph Waldo Emerson (1803-1882)",
			"Das schönste Geschenk, das die Götter den Menschen verliehen, ist die Freundschaft. Mögen manche auch den Reichtum, die Macht, die Ehre oder die Gesundheit preisen, ich ziehe Freundschaft und Weisheit allen anderen Gütern vor. Im Glück wie im Unglück verlangt der Mensch am meisten nach Freundschaft.\nMarcus Tullius Cicero (106-43 v.Chr.)",
			"Der höchste Beweis der Freundschaft ist nicht, einem Freund unsere Fehler, sondern ihm seine bemerkbar zu machen.\nFrancois La Rochefoucauld (1613-1680)",
			"Der Lohn der Freundschaft ist sie selbst. Wer sich mehr erhofft, versteht nicht, was wahre Freundschaft ist.\nAelred von Rievaulx (1110-1167)",
			"Die eigentliche Aufgabe eines Freundes ist, dir beizustehen, wenn du im Unrecht bist. Jedermann ist auf deiner Seite, wenn du im Recht bist\nMark Twain (1835-1910)",
			"Ein Freund - Die Hälfte meiner Seele.\nHoraz (65-8 v.Chr.)",
			"Freundschaft ist ein Zustand, der besteht, wenn jeder Freund glaubt, dem anderen gegenüber eine leichte überlegenheit zu haben.\nHonore de Balzac (1799-1850)",
			"Freundschaft ist weit tragischer als Liebe. Sie dauert länger.\nOscar Wilde (1854-1900)",
			"Freundschaft ist, wenn man beim ersten Wiedersehen nach langer Zeit das Gefühl hat, sich gerade erst gestern gesehen zu haben.",
			"Freundschaft ist wie Geld, leichter gewonnen als erhalten.\nSamuel Butler (1835-1902)",
			"Freundschaft, das ist eine Seele in zwei Körpern.\nAristoteles (384-322 v.Chr.)",
			"Freundschaft: Die stillschweigende übereinkunft zweier Feinde, die für gemeinsame Beute arbeiten wollen.\nElbert Hubbard (1856-1915)",
			"Freundschaft ist eine Tür zwischen zwei Menschen. Sie kann manchmal knarren, sie kann klemmen, aber sie ist nie verschlossen.\nBaltasar Gracian (1601-1658)",
			"Freundschaft ist ein Zustand, der besteht, wenn jeder Freund glaubt, dem anderen gegenüber eine leichte überlegenheit zu haben.\nHonore de Balzac (1799-1850)",
			"Freundschaft ist Gefühl und Verständnis füreinander und Hilfsbereitschaft in allen Lebenslagen.\nMarcus Tullius Cicero (106-43 v.Chr.)",
			"Freundschaft ist wie Geld, leichter gewonnen als erhalten.\nSamuel Butler (1835-1902)",
			"Freundschaft ist die Ehe der Seelen.\nVoltaire (1694-1778)",
			"Treue, Weisheit, Mut, Geduld und Liebe sind Freundschaft.\nJohann Kaspar Lavater (1741-1801)",
			"Wahre Freundschaft ist eine sehr langsam wachsende Pflanze.\nGeorge Washington (1732-1799)",
			"Zur Freundschaft gehört, dass wir einander gleichen, einander in einigem übertreffen, einander in einigem nicht erreichen.\nJean Paul (1763-1825)",
			"Danke, dass du das bist was du bist! Ich hoffe, dass du nie vergisst, dass das was du bist, etwas ganz Besonderes ist!",
			"Das größte Glück auf dieser Welt ist nicht ein Konto mit viel Geld.\nDas schönste ist, ich will's dir nennen, einen Menschen wie dich zu kennen.",
			"Die Blume blüht nur kurze Zeit, unsere Freundschaft blüht für alle Ewigkeit!",
			"Echte Freunde zu haben bedeutet viel im Leben, doch muss man die Freundschaft pflegen! Drum will ich dir endlich mal sagen, wie schön es ist, dich zum Freund haben!",
			"Egal ob nah, egal ob fern, es bleibt dabei, ich hab dich gern.",
			"Ein chinesisches Sprichwort sagt, dass man auf vieles verzichten kann, nur nicht auf gute Freunde, Freunde wie dich!",
			"Heute sind wir noch jung an Jahren, doch die Zeit eilt wie der Wind.\nIch hoffe, dass wir noch mit grauen Haaren, so richtig gute Freunde sind.",
			"Mein Freund! Ich wünsche dir so viel Glück wie der Regen Tropfen, so viel Liebe wie die Sonne Strahlen und so viel Freude, wie der Himmel Sterne hat.",
			"Mit dir ist das Leben so einfach wie eine Feder. Ohne dich ist es so schwer wie ein Stein.",
			"Nur der Tod kann unsere Freundschaft beenden.",
			"Unsere Freundschaft die soll wurzeln, bis wir in die Grube purzeln!",
			"Unsere Freundschaft soll nie schwanken, bis wir zusammen ins Altersheim wanken!",
			"Wahre Freundschaft ist etwas, das man nicht kaufen kann. Darum schenke ich dir meine! ",
			"Den wahren Freund erkennt man in der Not.\nMarcus Tullius Cicero (106-43 v.Chr.)",
			"Der Feind meines Feindes ist mein Freund.\nNapoleon Bonaparte (1769-1821)",
			"Der ist ein guter Freund, der hinter unserem Rücken gut von uns spricht.\nEnglisches Sprichwort",
			"Dein wahrer Freund ist nicht, wer dir den Spiegel hält der Schmeichelei,\nworin dein Bild dir selbst gefällt.\nDein wahrer Freund ist, wer dich sehen lässt deine Flecken\nund sie dir tilgen hilft, eh' Feinde sie entdecken.\nFriedrich Rückert (1788-1866)",
			"Der treue Freund ist wie Medizin im Leben, er ist ein wertvoller Schutz.\nJohannes Chrysostomus (um 380 n.Chr.)",
			"Ein wahrer Freund stellt sich dir nie in den Weg, es sei denn, es ginge mit dir bergab.\nMarcus Tullius Cicero (106-43 v.Chr.)",
			"Ein wahrer Freund zeigt dir deine Fehler und hat dich trotzdem gern.",
			"Nur wahre Freunde sagen dir, dass dein Gesicht schmutzig ist.",
			"Wahre Freunde sehen deine Tränen auch im Regen.",
			"Wirklich gute Freunde sind Menschen, die uns ganz genau kennen, und trotzdem zu uns halten.\nMarie von Ebner-Eschenbach (1830-1916)",
			"Ein Freund ist gleichsam ein anderes Ich.\nMarcus Tullius Cicero (106-43 v.Chr.)",
			"Einen sicheren Freund erkennt man in unsicherer Sache.\nMarcus Tullius Cicero (106-43 v.Chr.)",
			"Ein Freund ist einer, der alles von dir weiß, und der dich trotzdem liebt.\nElbert Hubbard (1856-1915)",
			"Ein Freund ist einer der kommt, wenn alle anderen gehen.",
			"Ein Freund ist einer, vor dem ich laut denken darf.\nRalph Waldo Emerson (1803-1882)",
			"Ein Freund ist ein Mensch, der dich so nimmt wie du bist und nicht so, wie er am wenigsten Schwierigkeiten mit dir hat.",
			"Ein Schmeichler ist ein Freund, der dir unterlegen ist oder vorgibt, es zu sein.\nAristoteles (384-322 v.Chr.)",
			"Freunde holen dich aus einer misslichen Lage heraus und erklären dir was du falsch gemacht hast.\nWahre Freunde sitzen neben dir und sagen: 'Scheiße, wir haben's versaut, aber geil war's!'",
			"Freunde sind die Menschen, die deine Vergangenheit akzeptieren, dich in der Gegenwart mögen, und in der Zukunft zu dir stehn.",
			"Freunde sind wie Sterne in der Nacht. Auch wenn sie manchmal nicht zu sehen sind, weißt du trotzdem, dass sie da sind.",
			"Gute Freunde beruhigen dich nicht, sie regen sich mit dir zusammen auf. ",
			"Alle Schätze dieser Erde wiegen einen guten Freund nicht auf.\nVoltaire (1694-1778)",
			"Alte Freunde sind wie alter Wein, er wird immer besser, und je älter man wird, desto mehr lernt man dieses unendliche Gut zu schätzen.\nFranz von Assisi (1182-1226)",
			"Ältere Bekanntschaften und Freundschaften haben neuen hauptsächlich das voraus, dass man sich einander schon viel verziehen hat.\nJohann Wolfgang von Goethe (1749-1832)",
			"Anteilnehmende Freundschaft macht das Glück strahlender und erleichtert das Unglück.\nMarcus Tullius Cicero (106 - 43 v.Chr.)",
			"Auf der höchsten Stufe der Freundschaft offenbaren wir dem Freunde nicht unsere Fehler, sondern die seinen.\nFrancois de La Rochefoucauld (1613-1680)",
			"Aus Freunden können Liebende werden, aber aus Liebenden können keine Freunde werden.\nWeisheit",
			"Bevor du anfängst deine Feinde zu lieben, solltest du deine Freunde besser behandeln.\nMark Twain (1835-1910)",
			"Bist du einmal reich auf Erden, wollen alle deine Freunde werden!\nBist du dann aber in der Not, sind alle deine Freunde tot!\nSprichwort",
			"Dauernde Freundschaft kann nur zwischen Menschen von gleichem Wert bestehen.\nMarie von Ebner-Eschenbach (1830-1916)",
			"Das Beste, was ein Mensch für einen andern tun kann, ist doch immer das, was er für ihn ist.\nAdalbert Stifter (1805-1868)",
			"Das erste Gesetz der Freundschaft lautet, dass sie gepflegt werden muss. Das zweite lautet: Sei nachsichtig, wenn das erste verletzt wird.\nVoltaire (1694-1778)",
			"Das Höchste, das ich für meinen Freund tun kann, ist einfach sein Freund sein. Wenn er weiß, dass es mich glücklich macht, ihn zu lieben, wird er keine andere Belohnung wollen.\nHenry David Thoreau (1817-1862)",
			"Der einzige Weg einen Freund zu haben ist der, selbst einer zu sein.\nRalph Waldo Emerson (1803-1882)",
			"Die Behauptung, jemand sei ein Freund, besagt in der Regel nicht mehr, als dass er kein Feind ist.\nHenry David Thoreau (1817-1862)",
			"Die Freunde nennen sich aufrichtig, die Feinde sind es.\nArthur Schopenhauer (1788-1860)",
			"Die Freundschaft, die der Wein gemacht, wirkt wie der Wein, nur eine Nacht.\nFriedrich von Logau (1605-1655)",
			"Die Freundschaft gehört zum Notwendigsten in unserem Leben. In Armut und im Unglück sind Freunde die einzige Zuflucht. Doch die Freundschaft ist nicht nur notwendig, sondern auch schön.\nAristoteles (um 350 v.Chr.)",
			"Die Liebe fragt die Freundschaft: Wofür bist du eigentlich da?\nDie Freundschaft antwortet der Liebe: Ich trockne die Tränen, die du angerichtet hast!",
			"Die schlechteste Münze, mit der man seine Freunde bezahlen kann, sind die Ratschläge. Nur die Hilfe ist die einzig gute.\nFerdinando Galiani(1728-1787)",
			"Du magst denjenigen vergessen, mit dem du gelacht hast, aber nie denjenigen, mit dem du geweint hast.\nKhalil Gibran (1883-1931)",
			"Ein bisschen Freundschaft ist mir mehr wert als die Bewunderung der ganzen Welt.\nOtto von Bismarck (1815-1898)",
			"Ein Leben ohne Freunde ist wie eine weite Reise ohne Gasthaus.\nDemokrit (lebte um 400 v.Chr.)",
			"Entfernung kann zwar Freunde trennen, doch wahre Freundschaft trennt sie nie.\nWeisheit",
			"Es gibt Menschen, deren einmalige Berührung mit uns für immer den Stachel in uns zurücklässt, ihrer Achtung und Freundschaft wert zu bleiben.\nChristian Morgenstern (1871-1914)",
			"Es gibt wenig aufrichtige Freunde. Die Nachfrage ist auch gering.\nMarie von Ebner-Eschenbach (1830-1916)",
			"Es ist die erste Freundespflicht, dem Freunde seine Illusionen zu lassen.\nArthur Schnitzler (1862-1931)",
			"Es ist schlimm, erst dann zu merken, dass man keine Freunde hat, wenn man Freunde nötig hat.\nPlutarch (um 100 n.Chr.)",
			"Es kommt nicht darauf an, dass Freunde zusammenkommen, sondern darauf, dass sie übereinstimmen.\nJohann Wolfgang von Goethe (1749-1832)",
			"Es sollt' ein Freund des Freundes Schwächen tragen.\nWilliam Shakespeare (1564-1616)",
			"Die Wunde, die ein Freund schlägt, heilt nie.",
			"Freundschaft fließt aus vielen Quellen, am reinsten aus dem Respekt.\nDaniel Defoe (1660-1731)",
			"Freundschaften sind wie Porzellanschüsseln, lässt du sie fallen, zerbrechen sie.",
			"Freundschaft gibt zwar nicht die Seeligkeit der Liebe, doch wäre ein Leben hart, das ohne Freunde bliebe.",
			"Freundschaft heißt vergessen, was man gab, und in Erinnerung behalten, was man empfing.\nAlexandre Dumas (1802-1870)",
			"Im Grunde sind es immer die Verbindungen mit Menschen, die dem Leben seinen Wert geben.\nWilhelm von Humboldt (1767-1835)",
			"In der Trockenheit erkennt man die guten Quellen, in der Not die guten Freunde.\nChinesisches Sprichwort",
			"Lachen ist durchaus kein schlechter Beginn für eine Freundschaft und ihr bei weitem bestes Ende.\nOscar Wilde (1854-1900)",
			"Mach Freundschaft mit eines Menschen Güte, nicht mit seinem Gut.\nChinesisches Sprichwort",
			"Man kommt in der Freundschaft nicht weit, wenn man nicht bereit ist, kleine Fehler zu verzeihen.\nJean de La Bruyère (1645-1696)",
			"Man merkt häufig erst zu spät, wer ein richtiger Freund ist. Erst dann, wenn man ihn vermisst.",
			"Man wird in der Regel keinen Freund dadurch verlieren, dass man ihm ein Darlehen abschlägt, aber sehr leicht dadurch, dass man es ihm gibt.\nArthur Schopenhauer (1788-1860)",
			"Mit einem kritischen Freund an der Seite kommt man immer schneller vom Fleck.\nJohann Wolfgang von Goethe (1749-1832)",
			"Nenne nie jemanden einen Freund, der dir anfangs Honig ums Maul schmiert, nur um anschließend die Bienen auf dich zu hetzen.",
			"Nicht Berechnung macht Menschen zu Freunden, sondern das Bedürfnis nach verständnisvoller Gemeinsamkeit.\nCicero (106-43 v.Chr.)",
			"Nicht jeder, der uns schont, ist ein Freund, nicht jeder, der uns tadelt, ein Feind.\nAugustinus von Hippo (354-430)",
			"Nimm dir nicht den zum Freunde, der dir nicht ebenbürtig ist.\nKonfuzius (551-479 v.Chr.)",
			"Nimm dir Zeit für deine Freunde, denn sonst nimmt die Zeit dir deine Freunde.",
			"Nur auf dem Weg der Freundschaft kann man einen Menschen richtig erkennen.\nAugustinus von Hippo (354-430)",
			"Sei gegen deine Freunde, ob sie Glück oder Unglück haben, immer derselbe.\nPeriander (um 550 v.Chr.)",
			"So notwendig wie die Freundschaft ist nichts im Leben.\nAristoteles (384-322 v.Chr.)",
			"Reich sind nur die, die wahre Freunde haben.\nThomas Fuller (1608-1661)",
			"Viele Menschen gehen im Leben ein und aus, aber nur ein Freund hinterlässt auch Fußabdrücke.",
			"Vielleicht muss man die Liebe gefühlt haben, um die Freundschaft richtig zu erkennen.\nNicolas Chamfort (1741-1794)",
			"Von allen Geschenken, die uns das Schicksal gewährt, gibt es kein größeres Gut als die Freundschaft, keinen größeren Reichtum, keine größere Freude.\nEpikur von Samos (um 300 v.Chr.)",
			"Wahre Freunde kann man nicht kaufen, denn Sie kommen von selbst und sind unbezahlbar.",
			"Wenn alle Menschen wüssten, was jeder über den anderen sagt, es gäbe keine vier Freunde auf der Welt.\nBlaise Pascal (1623-1662)",
			"Wenn die Jahre wachsen erkennt man den Wert der Freundschaft immer tiefer.\nAdalbert Stifter (1805-1868)",
			"Wenn du einen Freund hast, geh ihn oft besuchen; denn Dornen und Gestrüpp verwachsen den Weg, der nicht begangen wird.\nAsiatisches Sprichwort",
			"Wen wir am dringendsten brauchen, ist jemand, der uns dazu bringt, das zu tun, wozu wir fähig sind.\nRalph Waldo Emerson (1803-1882)",
			"Wer edle Freunde von sich stößt, der stößt sein eigenes Leben, das er liebte, von sich.\nSophokles (um 450 v.Chr.)",
			"Wieviel Unrecht kann die Umarmung eines Freundes wieder gutmachen.\nJean Jacques Rousseau (1712-1778)",
			"Wo du Freunde hast, hast du Schätze.\nPlautus (254-184 v.Chr.)",
			"Die Freunde nennen sich aufrichtig, die Feinde sind es.\nArthur Schopenhauer (1788-1860)",
			"Es gibt nur ein Problem, das schwieriger ist, als Freunde zu gewinnen: Sie wieder loszuwerden.\nMark Twain (1835-1910)",
			"Falsche Freunde fragen nach Kippen.\nWahre Freunde sind der Grund, warum du keine Kippen hast.",
			"Falsche Freunde gleichen unseren Schatten: Sie halten sich dicht hinter uns, solange wir in der Sonne gehen und verlassen uns, sobald wir ins Dunkel geraten.",
			"Falsche Freunde haben dich noch nie weinen sehen.\nWahre Freunde weinen mit dir.",
			"Ein Freund der dich verlässt, war keiner.",
			"Falsche Freunde sind wie ein verdorbenes Essen, sie liegen schwer im Magen.",
			"Falsche Freunde sind wie Schatten. Sie umgeben uns beim Sonnenschein und sind verschwunden, wenn es dunkel wird.\nMarcel Baumert",
			"Freunde sind sehr wichtig doch nicht immer aufrichtig. Drum sieh zu wer deine Freunde sind, denn sie halten zu dir bei Sturm und Wind.",
			"Freunde teilen sich in der Not in wahre und falsche Freunde auf. Der falsche Freund ist plötzlich sehr beschäftigt, wenn du seiner Hilfe bedarfst, während ein wahrer Freund nicht von deiner Seite weicht.",
			"Lieber einen wahren Freund als 100 falsche.",
			"Freundschaft ist etwas sehr Kostbares. Falsche Freundschaften auch, denn du lernst daraus!",
			"Ich mag nicht Freundlichkeit bei tückischem Gemüte.\nWilliam Shakespeare (1564-1616)",
			"In der heutigen Zeit musst du darauf gefasst sein, dass sich deine besten Freunde gegen dich wenden.\nTarik Özbay",
			"Lieber drei anständige Gegner, als einen falschen Freund.\nSprichwort",
			"Lieber keine Freunde als falsche Freunde.\nSprichwort",
			"Wahre Freunde zu finden ist sehr schwer, doch falsche Freunde gibt es wie Sand am Meer.",
			"Alea iacta est.vDer Würfel ist gefallen.\nGaius Iulius Caesar (100-44 v.Chr.)",
			"Amicus certus in re incerta cernitur.\nEinen sicheren Freund erkennt man in unsicherer Lage.\nvermutlich von Marcus Tullius Cicero (106-43 v.Chr.)",
			"Carpe diem.\nNutze den Tag.\nHoraz (65-8 v.Chr.)",
			"Cibi condimentum est fames.\nDer Speise Würze ist der Hunger.\nMarcus Tullius Cicero (106-43 v.Chr.)",
			"Dum excusare credis, accusas.\nWährend du dich zu entschuldigen glaubst, klagst du dich an.\nHieronymus (347-420)",
			"Faber est suae quisque fortunae.\nJeder ist seines Glückes Schmied.\nAppius Claudius Caecus (um 300 v.Chr.)",
			"Gutta cavat lapidem, non vi sed saepe cadendo.\nSteter Tropfen höhlt den Stein.\nvermutlich Ovid (um Christi Geburt)",
			"Homo homini lupus.\nDer Mensch ist des Menschen Wolf.\nPlautus (um 220 v.Chr.)",
			"In vino veritas.\nIm Wein liegt die Wahrheit.\nAlkaios (um 600 v.Chr.)",
			"Nomen est omen.\nDer Name ist ein Vorzeichen.\nPlautus (um 200 v.Chr.)",
			"Pecunia non olet.\nGeld stinkt nicht.\naus der römischen Kultur",
			"Scientia potentia est.\nWissen ist Macht.\nFrancis Bacon (1561-1626)",
			"Veni vidi vici.\nIch kam, ich sah, ich siegte.\nGaius Julius Caesar (100-44 v.Chr.)",
			"Vita brevis, ars longa.\nDas Leben ist kurz, die Kunst ist lang.\nHippokrates (um 400 v.Chr.) ",
			"Ergo bibamus.\nAlso lasst uns trinken!", "Errare humanum est.\nIrren ist menschlich.",
			"Festina lente.\nEile mit Weile.\nGriechisches Zitat", "Fiat lux!\nEs werde Licht!",
			"Gaudeamus igitur, iuvenes dum sumus!\nFreuen wir uns also, solange wir jung sind.",
			"Manus manum lavat.\nEine Hand wäscht die andere.", "Mea culpa.\nMeine Schuld.",
			"Mors certa, hora incerta.\nDer Tod ist gewiss, seine Stunde ungewiss.",
			"Nolens volens.\nOb man will oder nicht.", "Nulla vita sine musica.\nKein Leben ohne die Musik.",
			"Omne initium difficile est.\nAller Anfang ist schwer.", "Omnes ad loca!\nAlle auf die Plätze!",
			"Omnia tempus habent.\nAlles hat seine Zeit.", "Ora et labora.\nBete und arbeite!",
			"Pax vobiscum.\nFriede sei mit euch.", "Primus omnium.\nDer beste von allen.",
			"Quaere et invenies.\nSuche und du wirst finden.",
			"Quae nocent, docent.\nWas schadet, lehrt. (Durch Schaden wird man klug.)",
			"Quod erat demonstrandum.\nWas zu beweisen war.",
			"Quod licet Iovi non licet bovi!\nWas dem Jupiter erlaubt ist, ist dem Ochsen noch lange nicht erlaubt!",
			"Si vis amari, ama!\nWillst du geliebt werden, liebe! ", "Suum cuique.\nJedem das Seine.",
			"Tabula rasa.\nReinen Tisch machen",
			"Vinum bonum deorum donum.\nEin guter Wein ist ein Geschenk der Götter.",
			"Vita non optanda, optanda sunt vivanda.\nTräume nicht dein Leben, lebe deine Träume.",
			"Alles besiegt die Liebe, und wir geben uns ihr besiegt hin.\nVergil (70v.Chr. - 19 v.Chr.)",
			"Allmächtige Liebe! Göttliche! Wohl nennt man dich mit Recht, die Königin der Seelen.\nFriedrich von Schiller (1759-1805)",
			"Amor ist der größte Spitzbube unter den Göttern, der Widerspruch scheint sein Element zu sein.\nGiacomo Casanova (1725-1798)",
			"Die erste Wirkung der Liebe besteht darin, uns eine große Ehrfurcht einzuflößen.\nBlaise Pascal (1623-1662)",
			"Die Gewaltsamkeit der Liebe ist ebenso zu fürchten wie die des Hasses.\nHenry David Thoreau (1817-1862)",
			"Die Liebe trägt die Seele, wie die Füße den Leib tragen.\nKatharina von Siena (1347-1380)",
			"Die Liebe überwindet den Tod, aber es kommt vor, dass eine kleine üble Gewohnheit die Liebe überwindet.\nMarie von Ebner-Eschenbach (1830-1916)",
			"Die meisten Menschen brauchen mehr Liebe, als sie verdienen.\nMarie von Ebner-Eschenbach (1830-1916)",
			"Einen Menschen lieben, heißt ihn so zu sehen, wie Gott ihn gemeint hat.\nF.M. Dostojewski (1821-1881)",
			"Eine schöne Frau gefällt den Augen, eine gute dem Herzen. Die eine ist ein Kleinod, die andere ein Schatz.\nNapoleon Bonaparte (1769-1821)",
			"Es gibt nichts Schöneres, als geliebt zu werden, geliebt um seiner selbst willen oder vielmehr trotz seiner selbst.\nVictor Hugo (1802-1885)",
			"Das einzige Heilmittel bei Liebe ist noch mehr Liebe!\nHenry David Thoreau (1817-1862)",
			"Die Liebe die wir verschenken ist die einzige die uns bleibt.\nElbert Hubbard (1856-1915)",
			"Früchte reifen durch die Sonne, Menschen durch die Liebe.\nJulius Langbehn (1851-1907)",
			"In der Liebe gibt es einen Augenblick, wo sie sich selbst genügt, wo sie glücklich ist zu existieren.\nHonore de Balzac (1799-1850)",
			"Jede Leidenschaft verleitet uns zu Fehlern, die Liebe aber zu den lächerlichsten.\nFrancois de la Rochefoucauld (1613-1680)",
			"Jeder hat mal den Gedanken oder die Angst, dass man für die Person, die man liebt, nicht gut genug sei.\nTarik Özbay",
			"Wenn Treue Spaß macht, ist es Liebe.",
			"Liebe erblüht im Staunen einer Seele, die nichts erwartet und sie stirbt an der Enttäuschung des Ichs, das alles fordert.\nGustave Flaubert (1821-1880)",
			"Liebe hat kein Alter, sie wird ständig neu geboren.\nBlaise Pascal (1623-1662)",
			"Liebe sieht nicht mit den Augen, sondern mit dem Herzen.\nWilliam Shakespeare (1564-1616)",
			"Liebe und Glück sind Zwillinge, oder eines aus dem anderen geboren.\nWilliam Hazlitt (1778-1830)",
			"Mancher findet sein Herz nicht eher, als bis er seinen Kopf verliert.\nFriedrich Nietzsche (1844-1900)",
			"Nicht die Vollkommenen sind es, die Liebe brauchen, sondern die Unvollkommenen.\nOscar Wilde (1854-1900)",
			"Nirgends gibt es ein Gefängnis, zu dem Liebe nicht den Eintritt erzwingen kann.\nOscar Wilde (1854-1900)",
			"Ohne Liebe ist der Reiche arm, den Armen macht sie reich.\nAugustinus von Hippo (354-430)",
			"Ohne Liebe sind wir uns selbst zur Last, durch die Liebe tragen wir einander.\nAugustinus von Hippo (354-430)",
			"Platonische Liebe kommt mir so vor wie ein ewiges Zielen und niemals losdrücken.\nWilhelm Busch (1832-1908)",
			"Was aus Liebe getan wird, geschieht immer jenseits von Gut und Böse.\nFriedrich Nietzsche (1844-1900)",
			"Weniger wissen und mehr lieben steht höher im Kurs, als mehr wissen und nicht lieben.\nErasmus von Rotterdam (1466-1536)",
			"Wenn du liebst, dann hoffst du. Wenn du hoffst, dann wartest du. Wenn du wartest, dann bestehst du. Und wenn du auf etwas bestehst, dann gewinnst du.",
			"Wer keine Liebe fühlt, muss schmeicheln lernen, sonst kommt er nicht aus.\nJohann Wolfgang von Goethe (1749-1832)",
			"Wer nicht mehr liebt und nicht mehr irrt, der lasse sich begraben.\nJohann Wolfgang von Goethe (1749-1832)",
			"Wie Wind im Käfig, wie Wasser im Sieb, ist guter Rat im Ohr der Torheit und der Liebe.\nFriedrich Rückert (1788-1866) ",
			"Allein ist der Mensch ein unvollkommenes Ding; er muss einen Zweiten finden, um glücklich zu sein.\nBlaise Pascal (1623-1662)",
			"Besser eine geile Affäre, als eine miese Beziehung!",
			"Der Charakter einer Frau zeigt sich nicht, wo die Liebe beginnt, sondern wo sie endet.\nRosa Luxemburg (1871-1919)",
			"Die Liebe ist eine Dummheit, die zu zweit begangen wird.\nNapoleon Bonaparte (1769-1821)",
			"Einstimm'ges Lied hat keine Harmonie.\nWilliam Shakespeare (1564-1616)",
			"Es entspricht der Natur des Menschen, dass Liebe nicht mit der Zeit erkaltet, sondern dass sie sich erst mit der Zeit entwickelt.\nNikolai Gawrilowitsch Tschernyschewski (1828-1889)",
			"Es ist mit der Liebe auch wie mit anderen Pflanzen: Wer Liebe ernten will, muss Liebe Pflanzen.\nJeremias Gotthelf (1797-1854)",
			"Es muss von Herzen kommen, was auf Herzen wirken soll.\nJohann Wolfgang von Goethe (1749-1832)",
			"Freiwillige Abhängigkeit ist der schönste Zustand, und wie wäre der möglich ohne Liebe?\nJohann Wolfgang von Goethe (1749-1832)",
			"Glaube nicht, du kannst den Lauf der Liebe lenken, denn die Liebe, wenn sie dich für würdig hält, lenkt deinen Lauf.\nKhalil Gibran (1883-1931)",
			"In den Beziehungen zwischen Menschen gibt es so wenig einen Stillstand wie im Leben des Einzelnen.\nArthur Schnitzler (1862-1931)",
			"In der Liebe gilt Schweigen oft mehr als Sprechen.\nBlaise Pascal (1623-1662)",
			"Je mehr man liebt, um so tätiger wird man sein.\nVincent van Gogh (1853-1890)",
			"In Menschen wie in der Sprache ist alles Beziehung\nAntoine de Rivarol (1753-1801)",
			"Kämfe um das was du liebst, sonst wirst du es irgendwann bereuen nicht gekämpft zu haben.",
			"Kummer lässt sich allein tragen. Für das Glück sind zwei Menschen erforderlich.\nElbert Hubbard (1859-1915)",
			"Liebe ist kein Solo. Liebe ist ein Duett. Schwindet sie bei einem, verstummt das Lied.\nAdelbert von Chamisso (1781-1838)",
			"Mache niemanden zu deiner Priorität für den du nur eine Option bist.",
			"Niemals sind wir so verletzlich, als wenn wir lieben.\nSigmund Freud (1856-1939)",
			"Ob man jemanden wirklich liebt, weiß man oft erst, wenn man ihn verloren hat!",
			"Raum ist in der kleinsten Hätte, für ein glücklich liebend Paar.\nFriedrich von Schiller (1759-1805)",
			"Soweit die Erde Himmel sein kann, soweit ist sie es in einer glücklichen Ehe.\nMarie von Ebner-Eschenbach (1830-1916)",
			"Von Jugend an gepflegte Herzensbeziehungen sind doch das Schönste, was das Leben hat.\nTheodor Fontane (1819-1898)",
			"Wenn du etwas liebst, lass es frei. Kommt es zu dir zurück, gehört es dir. Wenn nicht, hat es dir nie gehört.\nKonfuzius (um 500 v.Chr.)",
			"Wenn man Liebe nicht bedingungslos geben und nehmen kann, ist es keine Liebe, sondern ein Handel.\nEmma Goldman (1869-1940)",
			"Wenn man verliebt ist, ist jedes Wetter wunderbar.\nRudyard Kipling (1865-1936)",
			"Die Ehe ist das einzige Abenteuer, in das sich die Feigen störzen.\nVoltaire (1694-1778)",
			"Die Ehe ist der Anfang und der Gipfel aller Kultur.\nJohann Wolfgang von Goethe (1749-1832)",
			"Die Ehe ist die Vereinigung zweier göttlicher Funken, auf dass ein dritter auf Erden geboren werde.\nKhalil Gibran (1883-1931)",
			"Die Ehe ist ein Zustand, in dem es zwei Leute weder mit noch ohne einander längere Zeit aushalten können.\nMarie von Ebner-Eschenbach (1830-1916)",
			"Die Ehe ist eine Gemeinschaft, bestehend aus einem Herrn, einer Herrin und zwei Sklaven, insgesamt zwei Personen.\nAmbrose Bierce (1842-1914)",
			"Die Ehe muss ständig gegen ein Gespenst ankämpfen, gegen das Gespenst der Gewohnheit.\nHonore Balzac (1799-1850)",
			"Die Liebe, welch lieblicher Dunst!\nDoch in der Ehe, da steckt die Kunst!\nTheodor Storm (1817-1888)",
			"Eine Ehe ist gut für diejenigen, die Angst davor haben, nachts allein zu schlafen.\nHieronymus (um 400 n.Chr.)",
			"Eine Ehe muss sich beständig gegen ein Monster verteidigen, welches alles ruinieren kann: Die Routine!\nHonore de Balzac (1799-1850)",
			"Eine gute Ehe beruht auf dem Talent zur Freundschaft\nFriedrich Nietzsche (1844-1900)",
			"Im Ehestand muss man sich manchmal streiten, denn dadurch erfährt man etwas voneinander.\nJohann Wolfgang von Goethe (1749-1832)",
			"Manche Ehe ist ein Todesurteil, das jahrelang vollstreckt wird.\nAugust Strindberg (1849-1912)",
			"Nicht der Mangel der Liebe, sondern der Mangel an Freundschaft macht unglückliche Ehen.\nFriedrich Nietzsche (1844-1900)",
			"Soweit die Erde Himmel sein kann, soweit ist sie es in einer guten Ehe.\nMarie von Ebner-Eschenbach (1830-1916)",
			"Wenn der Mann das Amt hat und die Frau den Verstand, dann gibt es eine gute Ehe.\nMarie von Ebner-Eschenbach (1830-1916)",
			"Die Ehe ist ein Versuch, zu zweit wenigstens halb so glücklich zu werden, wie man allein gewesen ist.\nOscar Wilde (1854-1900)",
			"Die zweite Ehe ist der Triumph der Hoffnung über die Erfahrung.\nSamuel Johnson (1709-1784)",
			"Ehe: Die einzige wirkliche Leibeigenschaft, die das Gesetz kennt.\nJohn Stuart Mill (1806-1873)",
			"Ehe: Gegenseitige Freiheitsberaubung im beiderseitigen Einvernehmen.\nOscar Wilde (1854-1900)",
			"Ehe ist wie ein Restaurantbesuch. Man denkt immer, man hat das Beste gewählt, bis man sieht, was der Nachbar bekommt.",
			"Eine gute Ehe wäre jene zwischen einer blinden Frau und einem tauben Mann.\nMichel de Montaigne (1533-1592)",
			"Gewisse Ehen halten nur in der Weise zusammen wie ineinander verbissene Tiere.\nGerhart Hauptmann (1862-1946)",
			"In der Ehe pflegt gewöhnlich immer einer der Dumme zu sein. Nur wenn zwei Dumme heiraten; das kann mitunter gut gehn.\nPaul Ambroise Valery (1871-1945)",
			"Männer sind lyrisch, Frauen episch, Ehen dramatisch\nNovalis (1772-1801)",
			"Mit der Ehe tauscht die Frau die Aufmerksamkeit vieler Männer gegen die Unaufmerksamkeit eines einzelnen ein.\nMaria Theresia (1717-1780)",
			"Der Mann ist eifersüchtig, wenn er liebt, die Frau, auch ohne, dass sie liebt, weil so viel Liebhaber, als von andern Frauen gewonnen worden, doch ihrem Kreise der Anbeter verloren sind.\nImmanuel Kant (1724-1804)",
			"Der Schmerz der Eifersucht ist deshalb so bitter, weil die Eitelkeit sich gegen ihn sträubt.\nStendhal (1783-1842)",
			"Die Eifersucht ist das schmutzigste aller Laster; in der Liebe gibt es keinerlei Eigentumsrecht.\nAugust Strindberg (1849-1912)",
			"Die Eifersucht ist die geistreichste Leidenschaft und trotzdem noch die größte Torheit.\nFriedrich Nietzsche (1844-1900)",
			"Die Eifersucht lässt dem Verstand niemals genügend Freiheit, um die Dinge zu sehen, wie sie sind!\nMiguel de Cervantes (1547-1616)",
			"Die Eifersucht wird zwar immer mit der Liebe geboren, aber sie stirbt nicht immer mit ihr.\nFrancois de La Rochefoucauld (1613-1680)",
			"Die Frauen würden sich ärgern, wenn ein Mann, den sie lieben, nicht eifersüchtig wäre.\nNinon de Lenclos (1620-1705)",
			"Neugier ist die Tochter der Eifersucht.\nMoliere (1622-1673)",
			"Eifersucht ist der Tod der Liebe.\nPedro Calderon de la Barca (1600-1681)",
			"Nichts auf der Welt trägt so viele Masken wie die Eifersucht.\nHermann Bahr (1863-1934)",
			"Stolzen Frauen kann die Eifersucht gefallen, weil sie ihnen auf eine neue Art ihre Macht zeigt.\nStendhal (1783-1842)",
			"Die Liebe im Herzen, die Gedanken bei dir, so lass ich mich nieder und träume von dir.",
			"Ein kleiner Kuss geschickt von mir, macht sich auf den Weg zu dir. Er trifft dich sanft und flüstert sacht: Ich liebe dich und gute Nacht!",
			"Ein kleiner Stern im weiten Raum, wünscht dir einen süßen Traum!",
			"Ich sag dir gute Nacht, und geb auf dich sehr acht. Ich bin ein kleiner überflieger und du mein kleiner Schmusetiger.",
			"Ich wünsch dir eine gute Nacht, hab grad an dich gedacht, \nträume süß und denk an mich, hab dich lieb vergiss das nicht.",
			"Ich wünsch dir eine gute Nacht,\nauf dass morgen die Sonne wieder für dich lacht.",
			"Schlaf gut ein, mein Schatzilein. Ich lieb dich sehr, mein Schatzibär. Du bist so toll, ich mag dich voll!",
			"Schließ deine Augen zu und schlaf schön ein. Ich werde in deinen Träumen bei dir sein. Werd dich verwöhnen und bewachen, was soll man sonst mit einem Engel machen?",
			"Weil du zum Verlieben bist und Küssen etwas schönes ist, schick ich dir zu Abendruh 1000 Küsse noch schnell zu!",
			"Wenn der Mond am Himmel schimmert, und ein kleines Sternchen glimmert, wünsch ich dir eine gute Nacht, hab ganz doll an dich gedacht.",
			"1000 Küsse send' ich dir, fang sie auf und träum' von mir!",
			"Darin besteht die Liebe: Dass sich zwei Einsame beschützen und berühren und miteinander reden.\nRainer Maria Rilke (1875-1926)",
			"Das erste Anzeichen wirklicher Liebe ist bei einem jungen Mann Schüchternheit, bei einem jungen Mädchen Kühnheit.\nVictor Hugo (1802-1885)",
			"Das große Glück in der Liebe besteht darin, Ruhe in einem anderen Herzen zu finden.\nJulie de Lespinasse (1732-1776)",
			"Liebe ist die Poesie der Sinne.\nHonore de Balzac (1799-1850)",
			"Das ist die wahre Liebe, die immer und immer sich gleich bleibt, ob man ihr alles gewährt, ob man ihr alles versagt.\nJohann Wolfang von Goethe (1749-1832)",
			"Das Schönste aber hier auf Erden ist lieben und geliebt zu werden.\nWilhelm Busch (1832-1908)",
			"Der Geist, der allen Dingen Leben verleiht, ist die Liebe.\nTschu-Li",
			"Die allerstillste Liebe ist die Liebe zum Guten.\nMarie von Ebner-Eschenbach (1830-1916)",
			"Die Hoffnung ist es, die die Liebe nährt.\nOvid (um Christi Geburt)",
			"Die Kraft verleiht Gewalt, die Liebe leiht Macht.\nMarie von Ebner-Eschenbach (1830-1916)",
			"Die Liebe ist der Endzweck der Weltgeschichte, das Amen des Universums.\nNovalis (1772-1801)",
			"Die Liebe ist die Schöpferin und Meisterin aller Dinge und Gottes älteste Gesellin.\nErnst Moritz Arndt (1769-1860)",
			"Die Liebe ist ein Stoff, den die Natur gewebt und die Phantasie bestickt hat.\nVoltaire (1694-1778)",
			"Die Liebe ist so unproblematisch wie ein Fahrzeug. Problematisch sind nur die Lenker, die Fahrgäste und die Straße.\nFranz Kafka (1883-1924)",
			"Die wahre Liebe ist keiner Untreue fähig.\nBettina von Arnim (1785-1859)",
			"Du fragst mich, Kind, was Liebe ist? Ein Stern in einem Haufen Mist.\nHeinrich Heine (1797-1856)",
			"Ein Tropfen Liebe ist mehr als ein Ozean Verstand.\nBlaise Pascal (1623-1662)",
			"Liebe besteht nicht nur darin, dass man einander ansieht, sondern, dass man gemeinsam in die gleiche Richtung blickt.\nAntoine de Saint-Exupery (1900-1944)",
			"Liebe ist nicht gleichbedeutend mit Beziehung. Liebe ist ein Phänomen. Ein Phänomen ist eine Erscheinung. Erscheinungen sind meist von kurzer Dauer.\nThomas Pfitzer (geb. 1961)",
			"Liebe ist eine Frühligsblume, die alles mit dem Duft der Hoffnung erfüllt, selbst die Ruinen, an denen sie sich emporrankt.\nGustave Flaubert (1821-1880)",
			"Liebe ist eine schwerwiegende Geisteskrankheit.\nPlato (um 400 v.Chr.)",
			"Liebe ist wie epidemische Krankheiten, je mehr man sie fürchtet, umso mehr setzt man sich ihr aus.\nNicolas Chanfort (1741-1794)",
			"Liebe ist wie Fieber; sie kommt und geht, ohne dass der Wille dabei eine Rolle spielt.\nHenri Beyle Stendhal (1783-1842)",
			"Welch eine himmlische Empfindung ist es, seinem Herzen zu folgen.\nJohann Wolfang von Goethe (1749-1832)",
			"Wenn Liebe nicht Verrücktheit ist, dann ist es Liebe.\nPedro Calderon de La Barca (1600-1681)",
			"Wer über die Liebe nachdenkt, der liebt nicht mehr.\nAugust von Kotzebue (1761-1819)",
			"Die Liebe ist das schönste Kleid.\n(Weisheit)",
			"Um einen guten Liebesbrief zu schreiben, musst du anfangen ohne zu wissen, was du sagen willst und endigen, ohne zu wissen, was du gesagt hast.\nJean-Jacques Rousseau (1712-1778)",
			"Deine Liebe ist wie das Salz in der Suppe, die Würze meines Lebens.",
			"Die Erde braucht Regen. Die Sonne braucht Licht. Der Himmel braucht Sterne und ich brauche dich!",
			"Du bist mein allergrößter Schatz, für einen anderen ist kein Platz.",
			"Du bist mein allergrößter Schatz. In meinem Herzen ist noch Platz. Der gehört dir ganz allein. Ich will für immer mit dir zusammen sein.",
			"Du bist mein Glück, du bist mein Stern, auch wenn du spinnst, ich hab dich gern.",
			"Du spielst die Hauptrolle in dem Film der sich 'Mein Leben' nennt.",
			"Ein Flirt war's nicht mal zu Beginn, doch etwas zog mich zur dir hin. Nun kommt es einfach über mich. Ich sage dir : Ich liebe dich!",
			"Ein kleines Mädchen erwartet keine Liebeserklärungen von seiner Puppe; es liebt sie, und damit gut. So sollte jede Liebe sein.\nRemy de Gourmont (1858-1915)",
			"Es lebt der Eisbär in Sibirien, es lebt in Afrika das Gnu, es lebt der Säufer in Delirien, in meinem Herzen lebst nur du!",
			"Ich bin dein kleiner Spatz und kenn nur einen Satz, piep, piep, piep, ich hab dich ganz doll lieb.",
			"Ich lieb dich jeden Tag so sehr, doch heute lieb ich dich noch mehr!",
			"Ich liebe dich, dich liebe ich. Das liebste für dich. In Liebe, ich.",
			"Ich liebe dir. Ich liebe dich. Wie man das schreibt, das weiß ich nicht. Ist die Grammatik auch nicht richtig. Ich liebe dir und das ist wichtig.",
			"Ich mag dich Tag für Tag, auch wenn ich's nicht persönlich sag. SMS find ich besser, es wird nicht rot und wirkt viel kesser.",
			"ICH muss dir was sagen, es hat was mit LIEBE zu tun. Falls es DICH interessiert, lies das Großgedruckte.",
			"Ich sag es kurz in einem Satz, du bist mein allergrößter Schatz!",
			"Ich wollt dir schon lange sagen, was ich für dich empfinde. Doch ich war zu schüchtern um zu wagen, dir meine Liebe anzutragen.",
			"Immer dann, wenn man am meisten fühlt, weiß man am wenigsten zu sagen.",
			"In meiner psychologischen Konstitution manifestiert sich eine absolute Dominanz positiver Effekte für die Individualität deiner Person. Kurz gesagt, ich hab dich lieb!",
			"Liebe macht süchtig, ich bin abhängig von dir.",
			"Lieber alles verlieren und dich haben, als alles zu haben und dich nicht.",
			"Man sagt, die größten Schätze liegen ganz tief unter der Erde.\nAber ich kann dich doch nicht einfach einbuddeln!",
			"Meine Liebe zu dir ist so groß und weit wie die Unendlichkeit.",
			"Mein Herz schlägt nicht mehr nur für mich, soll heißen: Ich liebe dich.",
			"Mit Haut und Haaren bin ich dein, mit Herz und Seele obendrein.",
			"Nie hätte ich geglaubt, dass es so etwas gibt, aber ich habe mich unsterblich in dich verliebt!",
			"Sieh nach oben, der helle Stern das bin ich. Ich werde dich das ganze Leben begleiten, denn ich liebe dich .",
			"Traue niemals einem Stern, Sterne funkeln und verglühen. Trauen niemals einer Rose, Rosen duften und verblühen. Traue jedoch ewig mir, denn mein ganzes Herz gehört nur dir!",
			"Wann immer wir uns sehen, muss ich dir gestehen, du bist die Einzige/ der Einzige für mich: Ich liebe dich.",
			"Wenn du mal nicht mehr kannst, gib mir deine Angst. Wenn die Hoffnung dich verlässt, halt dich an mir fest. Du bist wie ein Teil von mir, ich gehöre ewig dir.",
			"Warum ich dir schreibe? Weil ich an dich denke!\nWarum ich an dich denke? Weil ich dich vermisse!\nWarum ich dich vermisse? Weil ich dich liebe!",
			"Wer liebt, der lebt. Du hältst mich am Leben!",
			"Wo immer du bist, was immer du tust, ob du lachen oder weinen musst, ob dich jemand liebt oder alle dich hassen, auf mich kannst du dich immer verlassen.",
			"Worte allein werden niemals ausdrücken können, was ich für dich empfinde. Aber diese Worte sind schon einmal ein Anfang: Ich liebe dich von ganzem Herzen! ",
			"Ach, der Liebesschmerz ist der einzige Liebhaber so mancher stillen Mädchenseele.\nJeremias Gotthelf (1797-1854)",
			"Andere Mütter haben auch schöne Töchter/ Söhne.\nSprichwort",
			"Beim Kampf, Kopf gegen Herz, verliert immer die Leber.",
			"Besser eine verlorene Liebe, als nie geliebt zu werden.\nAlfred Tennyson (1809-1892)",
			"Das Herz hat seine Gründe, die der Verstand nicht kennt!\nBlaise Pascal (1623-1662)",
			"Das Herz ist noch nicht frei, noch bereit zur Liebe, wenn es nicht endlich müde ward, zu hassen.\nFernöstliche Weisheit",
			"Das schwere Herz wird nicht durch Worte leicht. Doch könnten Worte uns zu Taten führen.\nFriedrich Schiller (1759-1805)",
			"Dass du mich so traurig machst, sollte dir zeigen, wie glücklich du mich hättest machen können.",
			"Der Kummer, der nicht spricht, nagt leise an dem Herzen, bis es bricht.\nWilliam Shakespeare (1564-1616)",
			"Der Mensch sieht oft nur zu spät ein, wie sehr er geliebt wurde, wie vergesslich und undankbar er war und wie groß das verkannte Herz.\nJean Paul (1763-1825)",
			"Die Liebe lässt uns an Dinge glauben, denen wir sonst mit höchstem Misstrauen begegnen würden.\nPierre Carlet de Marivaux (1688-1763)",
			"Die Scherben einer Liebe lassen sich nie mehr zusammensetzen.\nSully Prudhomme (1839-1907)",
			"Die schlimmste Art einen Menschen zu vermissen ist neben ihm zu sitzen und zu wissen, dass er niemals wieder dir gehören wird.",
			"Die Vergangenheit, die mich immer wieder dazu zwingt, mich an dich zu erinnern, ist für die Gegenwart nicht von Bedeutung. Sie hinterlässt in der Zukunft nur Spuren des Schmerzes.\nTarik Özbay",
			"Die Zeit heilt keine Wunden, man gewöhnt sich nur an den Schmerz.",
			"Du dachtest wir würden uns in Freundschaft trennen. Ich dachte wir würden uns nie trennen.",
			"Du siehst wie ich lache, du siehst wie ich weine, du siehst wie frühlich ich bin, aber du siehst nicht meine Augen im tiefsten Inneren, denn da würdest du sehen, dass ich ohne dich nicht leben kann!",
			"Du sprachst von Liebe und meintest Sex. Du sprachst von Glück und meintest Befriedigung. Du sprachst von immer und meintest nie. Du sprachst von mir und meintest doch immer nur dich!",
			"Eher holst du den Vogel im Flug ein, als Liebe die flieht.\nArthur Schnitzler (1862-1931)",
			"Eine Beziehung ist wie Gras, sie wächst langsam und braucht ihre Zeit, bis irgendeine dumme Kuh kommt und alles zerstört.",
			"Es gibt immer einen Besiegten in der Liebe: Den, der mehr liebt.\nFranz Blei (1871-1942)",
			"Es ist leicht jemanden zu lieben den man zum ersten Mal gesehen hat, aber es ist schwer jemanden zu vergessen den man einmal geliebt hat!",
			"Es ist schlimm jemand zu vermissen aber viel schlimmer ist es jemand zu verlieren und ihn nie wieder zu sehen.",
			"Es ist schön verliebt zu sein, aber schrecklich zu wissen keine Chance zu haben.",
			"Tränen reinigen das Herz.\nFjodor Michailowitsch Dostojewski (1821-1881)",
			"Ich übersah die Wahrheit in deinen Augen, weil ich an die Wahrheit in deinen Worten glaubte.",
			"Ich will nicht die zweite Wahl sein, auch nicht die Erste, sondern die Einzige!",
			"In der Liebe ist der zuerst Geheilte immer der besser Geheilte.\nFrancois de La Rochefoucauld (1613-1680)",
			"In Liebesangelegenheiten wird man der Enttäuschungen nie müde.\nEmanuel Wertheimer (1846-1916)",
			"Irgendwann hört man auf zu warten und fängt an zu vergessen.",
			"Liebe ist wie eine Bergwanderung, wer die Höhen erklimmen will, muss auch die Täler durchwandern.",
			"Liebe! Liebe! Lass mich los!\nJohann Wolfgang von Goethe (1749-1832)",
			"Manchmal erwarten wir mehr von anderen, weil wir bereit wären genau so viel für sie zu tun.",
			"Manchmal hasst man den Menschen am störksten, den man am meisten liebt. Denn er ist der einzige, der einem weh tun kann.",
			"Manchmal merkt man erst wie sehr man jemanden geliebt hat wenn man gerade dabei ist diesen zu verlieren.",
			"Manchmal muss man einem Menschen den man liebt loslassen, damit er glücklich sein kann auch wenn man selbst daran zerbricht.",
			"Warte nicht, bis die richtige Person in dein Leben tritt. Sei die Person, die das Leben eines/er anderen bereichert.",
			"Was dir einmal viel bedeutet hat, wird dir nie egal sein.",
			"Was wir bis zur Leidenschaft lieben, das bringt uns schließlich um.\nGuy de Maupassant (1850-1893)",
			"Weißt du, was Liebe ist? Ein Wort, ein Gedanke, ein endloser Kuss. Aber Liebe ist mehr! Nimm mein Herz ernst, denn es spricht nur einmal zu dir.",
			"Wenn dir meine Augen gefallen, warum füllst du sie dann mit Tränen?",
			"Zeitweilige Einsamkeit ist für einen normalen Menschen notwendiger als Essen und Trinken.\nFjodor Dostojewskij (1821-1881)",
			"Das Übel ist nicht, ein paar Feinde zu hassen, sondern unsere Nächsten nicht genug zu lieben.\nAnton Tschechow (1860-1904)",
			"Der größte Gefallen, den wir anderen tun können, ist nicht, mit ihnen unsern Reichtum zu teilen, sondern ihnen ihren eigenen zu offenbaren.\nHenry David Thoreau (1817-1862)",
			"Die Gerechtigkeit ist nichts anderes als die Nächstenliebe des Weisen.\nGottfried Wilhelm Leibniz (1646-1716)",
			"In der Nächstenliebe gibt es kein Übermaß.\nFrancis Bacon (1561-1626)",
			"Lediglich der Schwache liebt nur sich, der Starke hat in seinem Herzen Platz für ganze Völker.\nIstvan Szechenyi (1791-1860)",
			"Liebe deinen Nächsten wie dich selbst.\nBibel",
			"Nächstenliebe ist der Drang nach neuem Eigentum.\nFriedrich Nietzsche (1844-1900)",
			"Unser Nächster ist jeder Mensch, besonders der, der unser Hilfe braucht.\nMartin Luther (1483-1546)",
			"Wir müssen unseren Nächsten lieben, entweder weil er gut ist oder damit er gut werde.\nAugustinus von Hippo (354-430)",
			"Als ich dich zum ersten Mal sah, da war mir klar: Es gibt sie wirklich, es ist verrückt, die Liebe auf den ersten Blick!",
			"Das Herz hat seine Gründe, die der Verstand nicht kennt.\nBlaise Pascal (1623-1662)",
			"Das Schönste, was ein Junge seinem Mädchen sagen kann, ist: 'Schatz, das nächste Mädchen, das ich lieben werde, wird unsere Tochter sein!'",
			"Der Tag an dem du gebohren wurdest war ein regnerischer Tag. Doch es war kein Regen, es war der Himmel der weinte, weil er einen Stern verloren hatte.",
			"Denn wo ich dein bin, bin ich ganz erst mein.\nMichelangelo (1475-1564)",
			"Ein Kuss von deinem Munde, ein Druck von deiner Hand, erinnert mich an jene Stunde, wo mein Herz das deine fand.\nähnlich einem alten Volkslied",
			"Für Tränen gibt es Taschentücher, für Wangen gibt es Küsse und für mich gibt es nur dich!",
			"Habe letzte Nacht meinen Schutzengel zu dir geschickt. Nach 5min kam er zurück und ich fragte wieso? Er lächelte und sagte: 'Ein Engel braucht keinen Schutzengel!'",
			"Hätte ich nur mehr 5 Minuten zu leben, hätte ich 4 Minuten mit dir verbracht und 1 Minute geweint. Aber nicht weil ich sterbe sondern weil ich dich nie wieder sehen würde.",
			"Ich denke einmal am Tag an dich, und dieser Gedanke dauert 24 Stunden lang.",
			"Ich fragte ihn: 'Hast du alles?' Er nahm meine Hand und sagte: 'Jetzt habe ich alles!'",
			"Ich habe heute ein paar Blumen nicht gepflückt, um dir ihr Leben zu schenken.\nChristian Morgenstern (1871-1914)",
			"Ich sehne mich nach dir, wie die Blume in der Hitze den Regen ersehnt, ich kann ohne dich nicht sein, wie der Winter nicht ohne den Frühling ist.",
			"Ich wünschte, ich wäre der Wind, der sanft durch deine Haare streicht, die Sonne, die dich zärtlich berührt und der Mond, der deinen Schlaf bewacht.",
			"Ich wünschte, ich wäre eine Träne von dir. Um in deinen Augen geboren zu sein, auf deinen Wangen zu leben und auf deinen Lippen zu sterben.",
			"In der Nähe in der Ferne, denkt ein kleines Herz an dich. Hat dich lieb und hat dich gerne, denkt das deine auch an mich?",
			"Manche Menschen sind der Meinung, dass die Sonne nur wegen ihnen aufgeht, aber bei dir dürfte das stimmen.",
			"Manche Tage sind länger als andere, manche Zeiten sind besser als andere, manche Träume sind schöner als andere, doch mein schönster Traum liest gerade diese Nachricht!",
			"Der Geist baut das Luftschiff, die Liebe aber macht gen Himmel fahren.\nChristian Morgenstern (1871-1914)",
			"Der Mensch, der dir ohne dich zu Berühren, ohne mit dir zu Sprechen, ein Lächeln ins Gesicht zaubern kann sollte der Mensch sein, dem du dein Herz schenkst!",
			"Glaube nie einem Jungen der sagt: 'Ich liebe dich'. Glaube ihm erst, wenn er mit Tränen in den Augen vor dir steht und fragt: 'Liebst du mich?'",
			"Ich habe heute ein paar Blumen nicht gepflückt, um dir ihr Leben zu schenken.\nChristian Morgenstern (1871-1914)",
			"Ich liebe dich. Gott schütze dich, er erhalte dich mir, er schütze und erhalte uns beide.\nLudwig van Beethoven (1770-1827)",
			"Mir ist auf der Straße ein sehr armer junger Mann begegnet, der verliebt war. Sein Hut war alt, sein Mantel abgetragen, Wasser rann durch seine Schuhe. Aber Sterne zogen durch seine Seele.\nVictor Hugo (1802-1885)",
			"Um einen Liebesbrief zu schreiben, musst du anfangen, ohne zu wissen, was du sagen willst, und endigen, ohne zu wissen, was du gesagt hast.\nJean-Jacques Rousseau (1712-1778)",
			"Wahre Liebe geht aus der Harmonie der Gedanken und dem Gegensatz der Charaktere hervor.\nTheodore Simon Jouffroy (1796-1842)",
			"Warum hat Gott uns nur 1 Herz gegeben? Gott hat jedem 2 Füße zum Laufen, 2 Hände zum Anfassen, 2 Ohren um zu hören und 2 Augen um zu sehen gegeben! Warum aber nur 1 Herz?\nWeil die andere Hälfte bei jemandem ist, den wir erst finden müssen.",
			"Wenn du eine Rose in der Wüste wärst, würde ich mich vor dich knien und weinen, damit du nicht verwelkst.",
			"Werd' ich zum Augenblicke sagen: Verweile doch! Du bist so schön! Dann magst du mich in Fesseln schlagen, dann will ich gern zugrunde gehn!\nJohann Wolfgang von Goethe (1749-1832)",
			"Zwei Dinge bedeuten mir Leben. Die Freiheit und die Frau, die ich liebe.\nVoltaire (1694-1778)",
			"Zwei schöne Lippen sind mehr wert als alle Beredsamkeit der Welt.\nGustave Flaubert (1821-1880)",
			"Das Wesen der Romantik ist die Ungewissheit.\nOscar Wilde (1854-1900)",
			"Nichts ist romantischer, als was wir gewöhnlich Welt und Schicksal nennen. Wir leben in einem kolossalen Roman.\nNovalis (1772-1801)",
			"Romantik, das ist der Liberalismus in der Literatur.\nVictor Hugo (1802-1885)",
			"Romantik: Lehre von der alleinseligmachenden Nützlichkeit.\nJoseph von Eichendorff (1788-1857)",
			"Abends, still und leise, ging ein Küsschen auf die Reise. Flog ganz heimlich hin zu dir und weisst du was, es war von mir.",
			"Blumen brauchen Sonnenschein, und ich brauch dich zum Glücklichsein!",
			"Deine Wärme spüre ich bis hier, doch leider bist du nicht bei mir, ich hoffe ich muss dich nicht noch lange vermissen und kann dich dann endlich wieder küssen!",
			"Dein Platz im Bett neben mir ist kalt und leer. Ich würd dich jetzt so gern berühren, dich einfach neben mir spüren. Ich vermisse dich so sehr!",
			"Denk an dich, Tag und Nacht. Was soll ich nur tun gegen diese Liebesmacht? Am liebsten wäre ich jetzt bei dir, denn mein Herz verlangt nach dir!",
			"Die liebe im Herzen, die Gedanken bei dir! So lege ich mich nieder und träume von dir.",
			"Ich denk an dich, was kann ich dafür, dass sich mein Herz so sehnet nach dir!",
			"Es gibt Freunde, es gibt Feinde und es gibt Menschen wie dich, die man vor lauter Liebe nie vergisst. Ich vermisse dich.",
			"Ich fürchte fast mein Herz verbrennt, wir lieben uns und sind zu oft getrennt. Die Welt ist grausam und gemein, ich würd jetzt zu gern bei dir sein!",
			"Ich lausche dem Wind und höre unsere Lieder. Ich schau auf die Uhr: Wann sehen wir uns endlich wieder?",
			"Ich sehe in den Himmel! Sehe die Sterne und die Ferne, ich denke an dich, vermisse dich! Verstehe mich, ich liebe dich!",
			"Ich vermisse deinen Atem, dem ich nachts lausche. Ich vermisse deine Hand, die mich sanft berührt. Ich vermisse deine Seele, die mich taumeln lässt. Ich vermisse dich!",
			"In der Nähe in der Ferne denkt ein kleines Herz an dich, hat dich lieb und hat dich gerne und vermisst dich fürchterlich!",
			"Jeden Tag denke ich an dich. In deinen Armen zu liegen, deine Nähe zu spüren und dich zu berühren. All das vermisse ich, wenn du nicht bei mir bist.",
			"Jeden Tag, jede Stunde, denke ich nur an dich! Ich vermisse dich jede Sekunde, ich liebe dich!",
			"Jeden Tag und jede Stunde, jede einzelne Sekunde, jeden Augenblick strahl ich vor Glück, wenn ich denk an dich zurück. Hoffe auf ein baldiges Wiedersehen, denn nur von Erinnerungen kann ich nicht leben.",
			"Kann nicht schlafen, kann nicht essen, kann deine Augen nicht vergessen.\nDie Zeit steht still, du bist so fern, du fehlst mir so, mein kleiner Stern!",
			"Mein Herz schreit: Wo bist Du?\nMein Herz weint: Nicht hier!\nMein Herz sagt: Ich vermisse Dich!\nMein Herz flüstert: Ich liebe Dich!",
			"Rosen sind rot, Veilchen sind blau. Die Schönste bist du, das weiss ich genau.",
			"Mit dir zu kuscheln und dich zu küssen, das werde ich diesen Abend vermissen.",
			"Der Staat ist heute jedermann, und jedermann kümmert sich um niemanden.\nHonore de Balzac (1799-1850)",
			"Der Staatsdienst muss zum Nutzen derer geführt werden, die ihm anvertraut werden, nicht zum Nutzen derer, denen er anvertraut ist.\nMarcus Tullius Cicero (um 70 v.Chr.)",
			"Nur die Lüge braucht die Stütze der Staatsgewalt, die Wahrheit steht von alleine aufrecht.\nBenjamin Franklin (1706-1790)",
			"Wir sind ein Volk und einig wollen wir handeln.\nFriedrich Schiller (1759-1805)",
			"Mit d'leit is' wia mit d' Viecha: Gib eana wos zan fressn und ruhig sans!\nMundartweisheit aus Österreich",
			"Alle Menschen sind klug - die einen vorher, die anderen nachher.\nVoltaire (1694-1778)",
			"Als Gott den Menschen erschuf, war er bereits müde, das erklärt manches.\nMark Twain (1835-1910)",
			"Der Weg des Lebens ist wie ein Fluss, nicht immer ist der Verlauf klar, aber wer stetig voranschreitet, wird seinen Weg finden.",
			"Die Leute die niemals Zeit haben, tun am wenigsten\nGeorg Christoph Lichtenberg (1742-1799)",
			"Bedenke, dass die menschlichen Verhältnisse allesamt unbeständig sind. Dann wirst du in glücklichen Zeiten nicht übermütig frühlich und in unglücklichen Zeiten nicht allzu traurig sein.\nSokrates (um 450 v.Chr.)",
			"Damit ein Anfang ist, wurde der Mensch geschaffen.\nAugustinus von Hippo (354-430)",
			"Das Denken ist zwar allen Menschen erlaubt, aber vielen bleibt es erspart.\nJohann Wolfgang von Goethe (1749-1832)",
			"Der Mensch gibt ebenso schwer eine Furcht auf als eine Hoffnung.\nOtto Ludwig (1813-1865)",
			"Der Mensch hofft immer Verbesserung.\nFriedrich Schiller (1759-1805)",
			"Der Mensch ist vielerlei. Aber vernünftig ist er nicht.\nOscar Wilde (1854-1900)",
			"Der Mensch ist weder Engel noch Tier und das Unglück will es, dass wer einen Engel aus ihm machen will, ein Tier aus ihm macht.\nBlaise Pascal (1623-1662)",
			"Der Mensch muss eine Plage haben; plagt ihn kein anderer, so plagt er sich selber.\nHeinrich Pestalozzi (1746-1827)",
			"Der moderne Mensch läuft zu leicht heiß, ihm fehlt zu sehr das Öl der Liebe.\nChristian Morgenstern (1871-1914)",
			"Die größte Niedertracht des Menschen ist sein Streben nach Ruhm, aber gerade dieses ist auch das Zeichen, dass er etwas Höheres ist.\nBlaise Pascal (1623-1662)",
			"Die größten Menschen sind jene, die anderen Hoffnung geben können.\nJean Jaures (1859-1914)",
			"Die Menschen aber, die ihren eigenen Weg zu gehen fähig sind, sind selten. Die große Zahl will nur in der Herde gehen, und sie weigert die Anerkennung denen, die ihre eigenen Wege gehen wollen.\nBlaise Pascal (1623-1662)",
			"Die Menschen glauben aufrichtig, die Ruhe zu suchen, und suchen in Wirklichkeit nur die Unrast.\nBlaise Pascal (1623-1662)",
			"Die Menschen sind füreinander geboren; belehre sie oder ertrage sie.\nMark Aurel (121-180)",
			"Die Menschen sind nicht immer, was sie scheinen, aber selten etwas besseres.\nGotthold Ephraim Lessing (1729-1781)",
			"Die Menschen werden durch Gesinnung vereinigt, durch Meinungen getrennt.\nJohann Wolfgang von Goethe (1749-1832)",
			"Ehrlichkeit ist ein teures Geschenk, das man von billigen Menschen nicht erwarten kann.",
			"Ein Mensch, der wahre Gottesfurcht im Herzen hat, ist wie die Sonne, die da scheint und wärmet, wenn sie auch nicht redet.\nMatthias Claudius (1740-1815)",
			"Ein wahrhaft großer Mensch verliert nie die Einfachheit eines Kindes.\nKonfuzius (um 500 v.Chr.)",
			"Enttäuscht vom Affen, schuf Gott den Menschen. Danach verzichtete er auf weitere Experimente.\nMark Twain (1835-1910)",
			"Es gibt Leute, die meinen, alles wäre vernünftig, was man mit einem ernsthaften Gesicht tut.\nGeorg Christoph Lichtenberg (1742-1799)",
			"Es gibt zwei Arten von Menschen: Gerechte, die sich für Sünder halten, und die anderen Sünder, die sich für Gerechte halten.\nBlaise Pascal (1623-1662)",
			"Es ist gut für den Menschen, am Ende des Jahres besser zu sein, als er am Anfang war.\nHenry David Thoreau (1817-1862)",
			"Es ist selten, dass ein Mensch weiß, was er eigentlich glaubt.\nOswald Spengler (1880-1936)",
			"Jeder Mensch hat ein Brett vor dem Kopf, es kommt nur auf die Entfernung an.\nMarie von Ebner-Eschenbach (1830-1916)",
			"Jeder Narr kann Regeln aufstellen und jeder Tor wird sich danach richten.\nHenry David Thoreau (1817-1862)",
			"Jedoch der schrecklichste der Schrecken, das ist der Mensch in seinem Wahn.\nFriedrich Schiller (1759-1805)",
			"Liebe alle Menschen, der Leidende aber sei dein Kind.\nMarie von Ebner Eschenbach (1830-1916)",
			"Alles Unheil dieser Welt geht davon aus, dass die Menschen nicht still in ihrer Kammer sitzen können.\nBlaise Pascal (1623-1662)",
			"Aus faulen Eiern werden keine Küken.\nWilhelm Busch (1832-1908)",
			"Bei einer Kerze ist nicht das Wachs wichtig, sondern das Licht.\nAntoine de Saint-Exupery (1900-1944)",
			"Bescheidenheit ist der Anfang aller Vernunft.\nLudwig Anzengruber (1839-1889)",
			"Bestätigung holt man sich dort, wo man annimmt sie auch zu finden.\nKlemens Oberforster ",
			"Die Augen sind die Fenster der Seele.\nHildegard von Bingen (1098-1179)",
			"Das Schweigen ist nach der Rede die zweite Großmacht der Welt.\nJean Baptiste Henri Lacordaire (1802-1861)",
			"Das Weltall ist ein Kreis, dessen Mittelpunkt überall, dessen Umfang nirgends ist.\nBlaise Pascal (1623-1662)",
			"Der eigene Hund macht keinen Lärm, er bellt nur.\nKurt Tucholsky (1890-1935)",
			"Der Verstand gleicht dem Holzwurm. Je tiefer er bohrt, umso dunkler wird es um ihn herum.\nLudwig Kasper (1883-1945)",
			"Die Basis einer gesunden Ordnung ist ein großer Papierkorb.\nKurt Tucholsky (1890-1935)",
			"Die Normalität ist eine gepflasterte Straße; man kann gut darauf gehen, doch es wachsen keine Blumen auf ihr.\nVincent van Gogh (1853-1890)",
			"Eine der angenehmen Seiten beim Lesen alter Briefe ist das Bewusstsein, dass man sie nicht beantworten muss.\nGeorge Gordon Byron (1788-1824)",
			"Ein Narr hat Glück in Masse, wer klug, hat selten Schwein.\nWilhelm Busch (1832-1908)",
			"Es gibt in der Welt selten ein schöneres übermaß als das der Dankbarkeit.\nJean de la Bruyere (1645-1696)",
			"Es kommt nicht darauf an, mit dem Kopf durch die Wand zu gehen, sondern mit den Augen die Tür zu finden.\nAlexander Macintosh (1861-1945)",
			"Es ließe sich alles trefflich schlichten, könnte man die Sachen zweimal verrichten.\nJohann Wolfgang von Goethe (1749-1832)",
			"Gehe ganz in deinen Handlungen auf und denke, es wäre deine letzte Tat.\nBuddha (um 500 v.Chr.)",
			"Gesegnet seien jene, die nichts zu sagen haben und den Mund halten.\nOscar Wilde (1854-1900)",
			"Gewohnheiten sind zuerst Spinnweben, dann Drähte.\nFernöstliche Weisheit",
			"Hass ist die Rache des Feiglings dafür, dass er eingeschüchtert ist.\nGeorge Bernard Shaw (1856-1950)",
			"Heiliges Tun! Von dir kommen alle Taten, und vom Nichtstun alle Untaten.\nHeinrich Pestalozzi (1746-1827)",
			"Jedes Ding hat drei Seiten: Eine die du siehst, eine die ich sehe und eine die wir beide nicht sehen.\nChinesische Weisheit",
			"Jedes Ding wird mit mehr Genuss erjagt als genossen.\nWilliam Shakespeare (1564-1616)",
			"Lästige Gedanken sind wie zudringliche Stechmücken.\nWilhelm Busch (1832-1908)",
			"Keine Gefahr ist dem Mut gewachsen.\nArabisches Sprichwort",
			"Man kann nicht jeden Tag etwas Großes tun, aber gewiss etwas Gutes.\nFriedrich Schleiermacher (1768-1834)",
			"Man muss aus der Stille kommen, um etwas Gedeihliches zu schaffen. Nur in der Stille wächst dergleichen.\nKurt Tucholsky (1890-1935)",
			"Mehr zu hören, als zu reden, solches lehrt schon die Natur: Sie versah uns mit zwei Ohren, doch mit einer Zunge nur.\nGottfried Keller (1819-1890)",
			"Nichts bedarf so sehr der Reform wie die Gewohnheiten anderer Leute.\nMark Twain (1835-1910)",
			"Solange man selbst redet, erfährt man nichts.\nMarie von Ebner-Eschenbach (1830-1916)",
			"Sprich langsam, aber handle schnell.\nMengzi (um 330 v.Chr.)",
			"Tadeln ist leicht, deshalb tun es so viele. Mit Verstand loben ist schwer, deshalb tun es so wenige.\nSprichwort",
			"überhaupt hat der Fortschritt das an sich, dass er viel größer ausschaut, als er wirklich ist.\nJohann Nestroy (1801-1862)",
			"Viele Spötter meinen reich an Geist zu sein und sind nur arm an Takt.\nGeorg Christoph Lichtenberg (1742-1799)",
			"Was man ernst meint, sagt man am besten im Spaß.\nWilhelm Busch (1832-1908)",
			"Wenn wir Schlittschuh über dünnes Eis laufen, liegt unser Heil nur in der Schnelligkeit.\nRalph Waldo Emerson (1803-1882)",
			"Wer auf offener See fährt, richtet sich nach den Sternen.\nWilhelm Busch (1832-1908)",
			"Wer das erste Knopfloch verfehlt, kommt mit dem Zuknüpfen nicht zu Rande.\nJohann Wolfgang von Goethe (1749-1832)",
			"Wer ein Messer wetzt, darf sich nicht wundern, wenn es anschließend scharf ist.\nKlemens Oberforster",
			"Wer für alles offen ist, kann doch nicht ganz dicht sein.",
			"Wer rudert, sieht den Grund nicht.\nWilhelm Busch (1832-1908)",
			"Würfel, Weiber, Wein bringen Lust und Pein.\nFriedrich von Logau (1605-1655)",
			"Zieh dich vor allem in dich selbst zurück, wenn du gezwungen bist, dich unter Leuten aufzuhalten.\nEpikur (um 300 v.Chr.)",
			"Nicht nur Taten, auch Worte können bleibende Eindrücke hinterlassen.\nKlemens Oberforster",
			"Das Leben der Eltern ist das Buch, in dem die Kinder lesen.\nAugustinus von Hippo (354-430)",
			"Der wahrhaft Edle predigt nicht, was er tut, bevor er nicht getan hat, was er predigt.\nKonfuzius (um 500 v.Chr.)",
			"Die Natur ist die beste Führerin des Lebens.\nMarcus Tullius Cicero (106-43 v.Chr.)",
			"Erfahrung ist der beste Lehrmeister. Nur das Schulgeld ist teuer.\nThomas Carlyle (1795-1881)",
			"Kluge Leute lernen auch von ihren Feinden.\nAristoteles (384-322 v.Chr.)",
			"Mit einem Herren steht es gut, der was er befohlen hat auch selber tut.\nJohann Wolfgang von Goethe (1749-1832)",
			"Mit nichts ist man freigebiger als mit Ratschlägen und mit nichts sollte man zurückhaltender sein.\nFrancois de La Rochefoucauld (1613-1680)",
			"Nimm Rat von allen, aber spare dein Urteil.\nWilliam Shakespeare (1564-1616)",
			"Sterne sind wie Ideale, unerreichbar, aber man kann sich stets an ihnen orientieren.\nCarl Schurz (1829-1906)",
			"Wirke auf andere durch das, was du bist.\nAlexander von Humboldt (1769-1859)",
			"Wie für unterschiedliche Jahreszeiten, so ziemt sich auch für unterschiedliche Lebensalter eine andere Handlungsweise.\nPlautus (um 200 v.Chr.)",
			"Wohl mit Recht bewundert man einen Herrn, der reiten kann.\nWilhelm Busch (1832-1908)",
			"Wollt ihr jemanden überzeugen, dass er schlecht lebt, müsst ihr recht leben; überzeugt ihn aber nicht mit Worten. Die Menschen glauben nur, was sie sehen.\nHenry David Thoreau (1817-1862)",
			"Zur Tugend, wie man zu sagen pflegt, ist eigentlich keiner recht aufgelegt.\nWilhelm Busch (1832-1908) ",
			"Ach wie viel ist doch verborgen, was man immer noch nicht weiß.\nWilhelm Busch (1832-1908)",
			"Alles Wissen besteht in einer sicheren und klaren Erkenntnis.\nRené Descartes (1596-1650)",
			"Das kleinste Kapitel eigener Erfahrung ist mehr wert, als Millionen fremder Erfahrung.\nGotthold Ephraim Lessing (1729-1781)",
			"Der Rest ist Schweigen.\nWilliam Shakespeare (1564-1616)",
			"Die Investition in Wissen bringt immer noch die besten Zinsen.\nBenjamin Franklin (1706-1790)",
			"Durch die Brille der Erfahrung wirst du beim zweiten Hinsehen klar sehen.\nHenrik Ibsen (1828-1906)",
			"Eine gute Entscheidung basiert auf Wissen und nicht auf Zahlen.\nPlato (um 400 v.Chr.)",
			"Ein guter Aphorismus ist die Weisheit eines ganzen Buches in einem einzigen Satz.\nTheodor Fontane (1819-1898)",
			"Es gibt mehr Dinge im Himmel und auf Erden, als eure Schulweisheit sich träumen lässt.\nWilliam Shakespeare (1564-1616)",
			"Ich weiß, dass ich nichts weiß.\nSokrates (470-399 v.Chr.)",
			"Immer, wenn man was wissen will, muss man sich auf die zweifelhafte Dienerschaft des Kopfes und der Köpfe verlassen.\nWilhelm Busch (1832-1908)",
			"Stets äußert sich der Weise leise, vorsichtig und bedingungsweise.\nWilhelm Busch (1832-1908)",
			"Wissen heißt handeln.\nSokrates (um 330 v.Chr.)", "Wissen ist Macht.\nFrancis Bacon (1561-1626)",
			"Wissenschaftlich ist nur eine Hälfte, Glauben ist die andere.\nNovalis (1772-1801) ",
			"Alles, was sich ein Mensch vorstellen kann, werden andere Menschen verwirklichen.\nJules Verne (1828-1905)",
			"Der, der sagt, es sei nicht möglich, sollte dem nicht im Wege stehen, der es tut.\nChinesischer Spruch",
			"Der eine wartet, dass die Zeit sich wandelt, der andere packt sie kräftig an und handelt.\nDante (1265-1321)",
			"Die Pläne fliegen machtvoll wie ein Adler, aber die Wirklichkeit hinkt wie ein alter Bettler.\nJapanisches Sprichwort",
			"Die Schwierigkeit ist immer klein, man muss nur nicht verhindert sein.\nWilhelm Busch (1832-1908)",
			"Die Welt gehört dem, der in Heiterkeit nach hohen Zielen wandert.\nRalph Waldo Emerson (1803-1882)",
			"Einen Nagel muss man ansetzen, bevor man ihn einschlagen kann.\nKlemens Oberforster",
			"Wer Kurs auf einen Stern nimmt, wankt nicht.\nLeonardo da Vinci (1452-1519)",
			"Gib nicht einfach auf, an das du jeden Tag denken musst.",
			"Große Werke werden nicht durch Stärke, sondern durch Beharrlichkeit vollbracht.\nSamuel Johanson (1709-1784)",
			"Jedes Schreckbild verschwindet, wenn man es fest ins Auge fasst.\nJohann Gottlieb Fichte (1762-1814)",
			"Man muss immer etwas haben, worauf man sich freut.\nEduard Mörike (1804-1875)",
			"Nichts ist unmöglich für den, der es versucht.\nAlexander der Große (356-323 v.Chr.)",
			"Nur wer nach den Sternen greift, streckt sich soweit er kann.\nKlemens Oberforster",
			"Viele sind hartnäckig in Bezug auf den einmal eingeschlagenen Weg, wenige in Bezug auf das Ziel.\nFriedrich Nietzsche (1844-1900)",
			"Wer vom Ziel nicht weiß, kann den Weg nicht haben.\nChristian Morgenstern (1871-1914)",
			"An alle Begierden soll man die Frage stellen: Was wird mir geschehen, wenn erfüllt wird, was die Begierde sucht, und was, wenn es nicht erfüllt wird?\nEpikur von Samos (um 300 v.Chr.)",
			"Aus der Nähe, aus der Ferne, denkt ein kleines Herz an Dich. Hat dich lieb und hat dich gerne, denkt das deine auch an mich?",
			"Bin ich ein Träumer komm sag es mir ich will es wissen nur von dir. Weil nur du die Wahrheit kennst weil nur du mir diese Träume schenkst.",
			"Das Staunen ist eine Sehnsucht nach Wissen.\nThomas von Aquin (1225-1274)",
			"Der leere Wunsch, die Zeit zwischem dem Begehren und dem Erwerben des Begehrten vernichtet zu werden, ist Sehnsucht.\nImmanuel Kant (1724-1804)",
			"Der Mond am Himmel scheint für dich, er sagt zu dir: 'Ich liebe Dich'. Er bringt eine Botschaft und flüstert zu Dir: 'Schlafe gut und träum von mir'!",
			"Die liebe im Herzen, die Gedanken bei dir! So lege ich mich nieder und träume von dir. Was kann ich dafür, das sich mein Herz so sehnt nach dir?",
			"Du bist wie die Sonne, und meine Gedanken wie Planeten, sie kreisen für alle Ewigkeit nur um dich.",
			"Du bist wie meine Seele, denn nur du weißt alles über mich. Du bist wie mein Herz, denn ohne dich kann ich nicht leben.",
			"Die Sehnsucht ist ein Verstörker des Wunsches.\nMarcel Proust (1871-1922)",
			"Ein Blick sagt mehr als 1000 Worte. Ich verlange nicht, dass du viele an mich verschwendest. Ich brauche nur einen Blick der sagt: 'Ich liebe dich!'",
			"Ein Lächeln das verzaubert, Augen die leuchten wie Sterne, Küsse die wie Feuer durch den Körper fließen. Ich schließe die Augen und sehe nur dich!",
			"Es war Liebe auf den ersten Blick, doch sie zerfiel Stück für Stück. Du hast nicht an sie geglaubt und mir doch mein Herz geraubt.",
			"Ich hab schon Sehnsucht wenn ich erwach, und sie wird immer störker bis tief in die Nacht.",
			"Ich denk an dich den ganzen Tag, weil ich dich so gerne mag!",
			"Jeder Same birgt eine Sehnsucht.\nKhalil Gibran (1883-1931)",
			"Kann nicht schlafen, kann nicht essen, kann deine Augen nicht vergessen. Die Zeit steht still, du bist so fern, du fehlst mir so mein kleiner Stern.",
			"Nicht wie Rosen, nicht wie Nelken die heute blühn und morgen welken, sondern wie das Immergrün soll auch unsere Liebe blühn!",
			"Sehnsucht involviert immer Ungeduld und bedeutet Leiden.\nPrentice Mulford (1834-1891)",
			"Vor lauter Sehnsucht ruft mein Herz, es möcht dich gern verstehen. Gewaltig ist der Herzensschmerz, willst du mich wiedersehen?",
			"Wenn des Nachts die Rosen weinen und mein Herz vor Sehnsucht bricht, will ich dir im Traum erscheinen und dir sagen: Ich liebe dich!",
			"Wo man am meisten drauf erpicht, grad das bekommt man meistens nicht.\nWilhelm Busch (1832-1908)",
			"Was ist Weisheit? Die Menschen kennen.\nWas ist Menschenwürde? Die Menschen lieben.\nKonfuzius (um 500 v.Chr.)",
			"Alle Menschen sind klug - die einen vorher, die anderen nachher.\nVoltaire (1694-1778)",
			"An einem edlen Pferd schätzt man nicht seine Kraft, sondern seinen Charakter.\nKonfuzius (um 500 v.Chr.)",
			"Aus dem Stein der Weisen macht der Dumme Schotter.\nDeutsches Sprichwort ",
			"Die Weisheit des Lebens besteht im Ausschalten der unwesentlichen Dinge.\nChinesische Weisheit",
			"Der beste Beweis für Weisheit ist beständige gute Laune.\nMichel de Montaigne (1533-1592)",
			"Die erste Stufe der Weisheit bedeutet, das Falsche einzusehen, die zweite, das Wahre zu erkennen.\nGotthold Ephraim Lessing (1729-1781)",
			"Die Gleichgültigkeit ist eine Lähmung der Seele, ein vorzeitiger Tod.\nAnton Tschechow (1860-1904)",
			"Die kürzesten Wörter, nämlich 'ja' und 'nein' erfordern das meiste Nachdenken.\nPythagoras von Samos (um 500 v.Chr.)",
			"Die überzeugung ist das Gewissen des Geistes.\nNicolas Chamfort (1741-1794)",
			"Es ist nicht wichtig, was du betrachtest, sondern was du siehst.\nHenry David Thoreau (1817-1862)",
			"Gesunder Menschenverstand in ungewöhnlichem Maße ist das, was die Welt Weisheit nennt.\nSamuel Taylor Coleridge (1772-1834)",
			"Jede Generation lacht über die alte Mode und folgt inbrünstig der neuen.\nHenry David Thoreau (1817-1862)",
			"Kein Feuer kann sich mit dem Sonnenschein eines Wintertages messen.\nHenry David Thoreau (1817-1862)",
			"Man sage nicht, das Schwerste sei die Tat:\nDa hilft der Mut, der Augenblick, die Regung.\nDas Schwerste dieser Welt ist der Entschluss.\nFranz Grillparzer (1791-1872)",
			"Menschlichkeit ist das Wesen der Sittlichkeit, Menschenkenntnis ist das Wesen der Weisheit.\nKonfuzius (um 500 v.Chr.)",
			"Niemand auf der Welt bekommt so viel dummes Zeug zu hören wie die Bilder in einem Museum.\nJules de Goncourt (1830-1870)",
			"Schweigen ist oft der lauteste Schrei.",
			"Tue das Gute vor dich hin und bekümmere dich nicht, was daraus werden wird.\nMatthias Claudius (1740-1815)",
			"Wahre Worte sind nicht immer schön; schöne Worte sind nicht immer wahr.\nLaotse (um 550 v.Chr.)",
			"Wenn du eine weise Antwort verlangst, musst du vernünftig fragen.\nJohann Wolfgang von Goethe (1749-1832)",
			"Wie dem Geiste nichts zu groß ist, so ist der Güte nichts zu klein.\nJean Paul (1763-1825)",
			"Zu wissen, was man weiß, und zu wissen, was man tut, das ist Wissen.\nKonfuzius (um 500 v.Chr.)",
			"Absicht ist die Seele der Tat.\nDeutsches Sprichwort",
			"Alle guten Dinge sind billig, alle schlechten teuer.\nHenry David Thoreau (1817-1862)",
			"Der Frosch im Brunnen ahnt nichts von der Weite des Meeres.\nChinesisches Sprichwort",
			"Der Himmel ist genauso unter unseren Füßen wie über unserem Kopf.\nHenry David Thoreau (1817-1862)",
			"Der siebente Tag hat einen Morgen, aber keinen Abend.\nThomas von Aquin (1224-1274)",
			"Der Weg zum Ziel beginnt an dem Tag, an dem du die 100%ige Verantwortung für dein Tun übernimmst.\nDante Alighieri (1265-1321)",
			"Ein einfacher Zweig ist dem Vogel lieber, als ein goldener Käfig.\nChinesisches Sprichwort",
			"Gedenke der Quelle, wenn du trinkst.\nChinesisches Sprichwort",
			"Gehen ist ein ständig aufgefangener Fall.\nKonfuzius (um 500 v.Chr.)",
			"Hinter jedem neuen Hügel dehnt sich die Unendlichkeit.\nWilhelm Busch (1832-1908)",
			"Jeder dumme Junge kann einen Käfer zertreten. Aber alle Professoren der Welt können keinen herstellen.\nArthur Schopenhauer (1788-1860)",
			"Jeder Mensch wird als Original geboren, aber die meisten sterben als Kopie\nKaspar Schmidt (1806-1856)",
			"Ein Lächeln kostet nichts, aber es ist viel wert.",
			"Solange Menschen denken, dass Tiere nicht fühlen, müssen Tiere fühlen, dass Menschen nicht denken.\nvermutlich eine Weisheit der Indianer",
			"Vügel singen nie in Höhlen.\nHenry David Thoreau (1817-1862)",
			"Was du mir sagst, das vergesse ich. Was du mir zeigst, daran erinnere ich mich. Was du mich tun lässt, das verstehe ich.\nKonfuzius (um 500 v.Chr.)",
			"Welch eine himmlische Empfindung ist es, seinem Herzen zu folgen.\nJohann Wolfgang von Goethe (1749-1832)",
			"Wenn auf der Erde die Liebe herrschte, wären alle Gesetze entbehrlich.\nAristoteles (um 350 v.Chr.)",
			"Wenn der Mensch nicht beizeiten von der Erde Abschied nimmt, so nimmt sie Abschied von ihm.\nFriedrich Hebbel (1813-1863)",
			"Wenn die Sprache nicht stimmt, dann ist das, was gesagt wird, nicht das, was gemeint ist.\nKonfuzius (551-479 v.Chr.)",
			"Wenn du die Welt verändern willst, beginne mit dem Menschen, den du jeden Morgen im Spiegel siehst.\nSimone Weil (1909-1943)",
			"Wir leben in einem Zeitalter, wo nur unnötige Dinge für uns notwendig sind.\nOscar Wilde (1854-1900)",
			"Wer nichts waget, der darf nichts hoffen.\nFriedrich Schiller (1759-1805)",
			"Wer Schmetterlinge lachen hört, der weiß wie Wolken riechen.\nNovalis (1772-1801)",
			"Abschiedsworte müssen kurz sein wie eine Liebeserklärung.\nTheodor Fontane (1819-1898)",
			"Das letzte Hemd hat keine Taschen.\nSprichwort",
			"Der ist reich, dem das Leben die Abschiede schwer machte.\nAlfred Grünewald (1884-1942)",
			"Ich schätze seine völlige Abwesenheit sehr.\nWilliam Shakespeare (1564-1616)",
			"Nun geh' hinfort und komm' nie wieder, ach was, wir freun uns auf dich immer wieder!\nKlemens Oberforster",
			"Abschied ist immer ein wenig Sterben.",
			"Ein Abschied verleitet immer dazu, etwas zu sagen, was man sonst nicht ausgesprochen hätte.\nMichel de Montaigne (1533-1592)",
			"Ein Abschied schmerzt immer, auch wenn man sich schon lange darauf freut.\nArthur Schnitzler (1862-1931)",
			"Heitere Resignation - es gibt nichts Schöneres.\nMarie von Ebner-Eschenbach (1830-1916)",
			"Im Abschied liegt die Geburt der Erinnerung.\nSprichwort",
			"Was er ist und was er war, das wird uns erst beim Abschied klar.\nSprichwort",
			"Wohin du auch gehst, geh mit deinem ganzen Herzen.\nKonfuzius (um 500 v.Chr.)",
			"Beim Abschied wird die Zuneigung zu den Sachen, die uns lieb sind, immer ein wenig wärmer.\nMichel de Montaigne (1533-1592)",
			"Ein guter Abgang ziert die Übung.\nFriedrich Schiller (1759-1805)",
			"In jeder großen Trennung liegt ein Keim von Wahnsinn. Man muss sich hüten, ihn nachdenklich auszubrüten und zu pflegen.\nJohann Wolfgang von Goethe (1749-1832)",
			"Kein Abschied auf der Welt fällt schwerer als der Abschied von der Macht.\nCharles Maurice de Talleyrand (1754-1838)",
			"Klug ist, wer stets zur rechten Stunde kommt, doch klüger, wer zu gehen weiß, wann es frommt.\nEmanuel Geibel (1815-1884)",
			"Man muss es immer dahin bringen, dass man zurückgewünscht wird.\nBaltasar Gracian Morales (1601-1658)",
			"Man sieht die Sonne langsam untergehen und erschrickt doch, wenn es plötzlich dunkel ist.\nFranz Kafka (1883-1924)",
			"Meistens hat, wenn zwei sich scheiden, einer etwas mehr zu leiden.\nWilhelm Busch (1832-1908)",
			"Man schreibt nicht so ausführlich, wenn man den Abschied gibt.\nHeinrich Heine (1797-1856)",
			"Wenn einem alles gegen den Strich geht, sollte man einen Punkt machen.\nWeisheit",
			"Wenn Menschen auseinandergehen, so sagen sie: Auf Wiedersehen!\nErnst von Feuchtersleben (1806-1849)",
			"Wird man gebraucht, erfüllt man seine Pflicht. Wird man nicht mehr gebraucht, so zieht man sich zurück.\nKonfuzius (um 500 v.Chr.) ",
			"Achtung ist mehr als Beachtung, Ansehen mehr als Ruf. Ehre mehr als Ruhm.\nNicolas-Sebastien Chamfort (1741-1794)",
			"Achtung verdient, wer erfüllt, was er vermag.\nSophokles (um 450 v.Chr.)",
			"Alle Autorität, die ich besitze, beruht einzig darauf, dass ich weiß, was ich nicht weiß.\nSokrates (um 450 v.Chr.)",
			"Bestätige deine Worte mit der Tat.\nSeneca (um Christi Geburt)",
			"Das ist das Allergrößte, wenn ich des Nächsten Schwachheit tragen kann.\nMartin Luther (1483-1546)",
			"Den nenn' ich vornehm, der sich streng bescheiden die eigene Ehre gibt und wenig fragt, ob ihn die Nachbarn lästern oder neiden.\nPaul Heyse (1830-1914)",
			"Die Ehre des Mannes besteht darin, was die Leute denken, des Frauenzimmers aber, was sie sprechen.\nImmanuel Kant (1724-1804)",
			"Die Ehre, einmal erkrankt und dann nicht rasch geheilt, steht niemals wieder von den Toten auf.\nFriedrich Hebbel (1813-1863)",
			"Die Ehre ist das äußere Gewissen und das Gewissen die innere Ehre.\nArthur Schopenhauer (1788-1860)",
			"Der Ehre kannst du wohl von andern leicht entbehren. Wenn du dich selber nur zu halten weißt in Ehren.\nFriedrich Rückert (1788-1866)",
			"Die Ehr ist wie ein Reh, fleucht, als sie kommt, viel eh.\nFriedrich von Logau (1605-1655)",
			"Der wahrhaft Edle predigt nicht, was er tut, bevor er nicht getan hat, was er predigt.\nKonfuzius (um 500 v.Chr.)",
			"Die Kunst zu schweigen ist größer als die Kunst zu reden.\nArthur Schopenhauer (1788-1860)",
			"Durch Sanftmut triff den Zornigen,\nden Lügner durch das wahre Wort.\nSchenkend besieg den Geizigen,\nden Bösen durch die gute Tat.\nBuddha (um 500 v.Chr.)",
			"Ohne Geld ist die Ehre nur eine Krankheit.\nJean Baptiste Racine (1639-1699)",
			"Ehre ist die Mystik der Rechtlichkeit.\nFriedrich Schlegel (1772-1829)",
			"Ehre, wem Ehre gebührt!\nJohann Wolfgang von Goethe (1749-1832)",
			"Ein geistreicher Mann ist nur etwas wert, wenn er Charakter hat.\nNicolas-Sebastien Chamfort (1741-1794)",
			"Ein vornehmer Mensch tadelt sich selbst, ein gewöhnlicher die anderen.\nKonfuzius (um 500 v.Chr.)",
			"Es gehört viel mehr Mut dazu, seine Meinung zu ändern, als ihr treu zu bleiben.\nFriedrich Hebbel (1813-1863)",
			"Es gibt Größe, die auf menschlicher Konvention beruht, und natürliche Größe.\nBlaise Pascal (1623-1662)",
			"Es ist eine interessante Frage, bis zu welchem Grad die Menschen ihren relativen Rang behaupten würden, wenn sie ihrer Kleider entledigt wären.\nHenry David Thoreau (1817-1862)",
			"Es ist mehr wert, jederzeit die Achtung der Menschen zu haben, als gelegentlich ihre Bewunderung.\nJean Jacques Rousseau (1712-1778)",
			"Es ist nicht etwas so seltenes, dass einer seine Ehre für ein Ehrenzeichen verkauft.\nWilhelm Jakobs (1858-1942)",
			"Es kann die Ehre dieser Welt dir keine Ehre geben. Was dich in Wahrheit hebt und hält, muss in dir selber leben.\nTheodor Fontane (1819-1898)",
			"Falsche Bescheidenheit ist die schicklichste aller Lügen.\nNicolas-Sebastien Chamfort (1741-1794)",
			"Je weniger er Ruhm suchte, um so sicherer erreichte er ihn.\nThomas von Aquin (1224-1274)",
			"Lächeln ist die beste Art, jemandem die Zähne zu zeigen.\nChinesische Weisheit",
			"Lieber mit der Wahrheit fallen als mit der Lüge siegen.\nAugustinus von Hippo (354-430)",
			"Lieber von einer Hand, die wir nicht drücken möchten geschlagen, als von ihr gestreichelt werden.\nMarie von Ebner Eschenbach (1830-1916)",
			"Nicht woher ihr kommt, mache euch fürderhin eure Ehre, sondern wohin ihr geht.\nFriedrich Nietzsche (1844-1900)",
			"Nie hat Ehre noch erstritten, der sie andern abgeschnitten.\nFriedrich Haug (1761-1829)",
			"Ruhm ist der Vorzug, denen bekannt zu sein, die einen nicht kennen.\nNicolas-Sebastien Chamfort (1741-1794)",
			"Schade, dass die Weltverbesserer nie bei sich anfangen.\nMark Twain (1835-1910)",
			"Tugend ist der einzige Adel.\nBenjamin Franklin (1706-1790)",
			"Wer gleichgültig gegen die Ehre ist, ist auch gleichgültig gegen die Schande.\nKarl Julius Weber (1767-1832)",
			"Wer mit der eigenen Ehre schlecht verfahren, wird auch der anderen Ehre nicht bewahren.\nSaadi (um 1250) ",
			"Als ich klein war, glaubte ich, Geld sei das wichtigste im Leben. Heute, da ich alt bin, weiß ich: Es stimmt.\nOscar Wilde (1954-1900)",
			"Bankier: Ein Mensch, der seinen Schirm verleiht, wenn die Sonne scheint, und ihn sofort zurückhaben will, wenn es zu regnen beginnt.\nMark Twain (1835-1910)",
			"Beim Geld hört die Gemütlichkeit auf.\nDavid Hansemann (1790-1864)",
			"Das Geld, das man besitzt, ist das Mittel zur Freiheit, dasjenige, dem man nachjagt, das Mittel zur Knechtschaft.\nJean-Jacques Rousseau (1712-1778)",
			"Borgen bringt Sorgen\nSprichwort",
			"Das Geld gleicht dem Seewasser. Je mehr davon getrunken wird, desto durstiger wird man.\nArthur Schopenhauer (1788-1860)",
			"Der Armut fehlt vieles, dem Geiz alles.\nSeneca (zur Zeit Christi)",
			"Das Geld vermag es, eine Unzahl von Mängeln zu verdecken.\nWeisheit aus China",
			"Der Charme des Geldes liegt in seiner Menge.\nCarl Fürstenberg (1850-1933)",
			"Die Dinge haben nur den Wert, den man ihnen gibt.\nMolière (1622-1673)",
			"Die Liebe zum Geld ist die Wurzel von allem Bösen.\nBibel (Timotheus)",
			"Die Menschen verstehen nicht, welch große Einnahmequelle in der Sparsamkeit liegt.\nCicero (106-43 v.Chr.)",
			"Die Wege, auf denen man Geld gewinnen kann, führen fast ausnahmslos abwärts.\nHenry David Thoreau (1817-1862)",
			"Es gibt drei treue Freunde, eine alte Ehefrau, ein alter Hund und flüssiges Geld.\nBenjamin Franklin (1706-1790)",
			"Es ist frevelhaft, mit dem Geld und mit dem Feuer nicht vorsichtig umzugehen.\nAdalbert Stifter (1805-1868)",
			"Es ist leichter über Geld zu schreiben, als es zu verdienen. Und diejenigen, die Geld verdienen, spotten daher viel über jene, die nur darüber schreiben können.\nVoltaire (1694-1778)",
			"Ein Defizit ist, was man hat, wenn man weniger hat, als man hätte, wenn man gar nichts hat.",
			"Eine Hand voll Geld ist besser als beide Hände voller Ratschläge.\nSprichwort",
			"Geld ist meistens dort zu finden, wo es hingeschafft und nicht wo es verdient wird.\nKlemens Oberforster",
			"Geld ist geklontes Glück.\nChristian Wagner (1835-1918)",
			"Geld ist geprägte Freiheit.\nFjodor Michailowitsch Dostojewski (1821-1881)",
			"Geld ist nicht alles, aber es hat einen großen Vorsprung vor allem, was danach kommt.\nSprichwort",
			"Geld kostet zuviel.\nRalph Waldo Emerson (1803-1882)",
			"Geld ist die reinste Form des Werkzeugs.\nGeorg Simmel (1858-1918)",
			"Keine Festung ist so stark, dass Geld sie nicht einnehmen kann.\nCicero (106-43 v.Chr.)",
			"Kein Geld ist vorteilhafter angewandt als das, um welches wir uns haben prellen lassen: denn wir haben dafür unmittelbar Klugheit eingehandelt.\nArthur Schoppenhauer (1788-1860)",
			"Man wird in der Regel keinen Freund dadurch verlieren, dass man ihm ein Darlehen abschlägt, aber sehr leicht dadurch, dass man es ihm gibt.\nArthur Schopenhauer (1788-1860)",
			"Man kann nicht aus allem Gold, aber aus sehr vielem zumindest Kupfer machen.\nKlemens Oberforster",
			"Papiergeld kehrt früher oder später zu seinem inneren Wert zurück - Null.\nVoltaire (1694-1778)",
			"Wenn ein Mensch behauptet, mit Geld ließe sich alles erreichen, darf man sicher sein, dass er nie welches gehabt hat.\nAristoteles (384-322 v.Chr.)",
			"Wer der Meinung ist, dass man für Geld alles haben kann, gerät leicht in den Verdacht, dass er für Geld alles zu tun bereit ist.\nBenjamin Franklin (1706-1790)",
			"Wenn der Pöbel aller Sorten tanzet um die goldnen Kälber, halte fest: Du hast vom Leben doch am Ende nur dich selber.\nTheodor Storm (1817-1888)",
			"Wenn du den Wert des Geldes kennenlernen willst, versuche dir welches zu leihen.\nBenjamin Franklin (1706-1790)",
			"Wer Geld hat sollte es ausgeben. Dumm nur, dass das auch viele tun die keines haben.\nKlemens Oberforster",
			"Wer Geld in seinen Taschen hat, hat die in der Tasche, die keines besitzen.\nLeo Tostoi (1828-1910)",
			"Wer viel Geld hat, ist reich. Wer keine Krankheit hat, ist glücklich!\nChinesische Weisheit",
			"Willst du den Wert des Geldes erkennen, versuche dir welches zu borgen.\nBenjamin Franklin (1706-1790)",
			"Wir müssen sparen, wo es geht. Koste es was es wolle!",
			"Zeit ist Geld - Time is money.\nBenjamin Franklin (1706-1790)",
			"Der zum ersten Mal anstelle eines Speeres ein Schimpfwort benutzte, war der Begründer der Zivilisation.\nSigmund Freud (1856-1939)",
			"Die Ersetzung der Macht des Einzelnen durch die der Gemeinschaft ist der entscheidende kulturelle Schritt.\nSigmund Freud (1856-1939)",
			"Die Feindschaft schlägt dem Herzen weit tiefere Wunden, als je ein Mensch dem Feinde Wunden schlagen kann.\nAugustinus von Hippo (354-430)",
			"Es gab noch nie einen guten Krieg oder einen schlechten Frieden.\nBenjamin Franklin (1706-1790)",
			"Je störker wir sind, desto unwahrscheinlicher ist der Krieg.\nOtto von Bismarck (1815-1898)",
			"Lügen können Kriege in Bewegung setzen, Wahrheiten hingegen können ganze Armeen aufhalten.\nOtto von Bismarck (1815-1898)",
			"Man vergisst vielleicht, wo man die Friedenspfeife vergraben hat. Aber man vergisst niemals, wo das Beil liegt.\nMark Twain (1835-1910)",
			"Nicht wer zuerst die Waffen ergreift, ist Anstifter des Unheils, sondern wer dazu nötigt.\nNiccolo Machiavelli (1469-1527)",
			"Strenge gebiert Furcht, Grobheit aber gebiert Hass.\nFrancis Bacon (1561-1626)",
			"Wer den Feind umarmt, macht ihn bewegungsunfähig.\nSprichwort",
			"Der ungerechteste Frieden ist immer noch besser als der gerechteste Krieg.\nMarcus Tullius Cicero (106-43 v.Chr.)",
			"Du sollst nicht töten, hat einer gesagt.\nUnd die Menschheit hört es, und die Menschheit klagt. Will das niemals anders werden? Krieg dem Kriege! Und Friede auf Erden.\nKurt Tucholsky (1890-1935)",
			"Wenn auf der Erde die Liebe herrschte, wären alle Gesetze entbehrlich.\nAristoteles (um 350 v.Chr.)",
			"Wer noch nie einem Feind verziehen hat, hat noch nie eine der höchsten Lebensfreuden genossen.\nJohann Kaspar Lavater (1741-1801)",
			"Wir würden viel Frieden haben, wenn wir uns nicht so viel mit dem,was andere reden und tun, beschäftigen würden.\nThomas von Kempen (1380-1471) ",
			"Aller Herrschaft Druck ist schwer. Man muss den Menschen immer freiwillig handeln zu lassen scheinen.\nJohann Jakob Wilhelm Heinse (1749-1803)",
			"Das Geheimnis der Macht besteht darin, zu wissen, dass andere noch feiger sind als wir.\nLudwig Börne (1786-1837)",
			"Die Kraft eines Riesen zu besitzen ist wunderbar. Sie wie ein Riese zu gebrauchen ist Tyrannei.\nWilliam Shakespeare (1564-1616)",
			"Die Macht der Könige ist auf die Vernunft und auf die Torheit des Volkes gegründet und viel mehr auf die Torheit.\nBlaise Pascal (1623-1662)",
			"Ein Dummkopf bleibt ein Dummkopf nur für sich, in Feld und Haus, doch wie du ihn zu Einfluss bringst, so wird ein Schurke draus.\nFranz Grillparzer (1791-1872)",
			"Ein Zepter in der Hand und eine Krone auf dem Kopf machen noch keinen König.\nKlemens Oberforster",
			"Es muss verschiedene Rangstufen geben, da alle Menschen herrschen wollen und nicht alle es können.\nBlaise Pascal (1623-1662)",
			"Kein Abschied auf der Welt fällt so schwerer wie der Abschied von der Macht.\nCharles-Maurice de Talleyrand-Perigord (1754-1838)",
			"Kein Mensch ist gut genug, einen anderen Menschen ohne dessen Zustimmung zu regieren.\nAbraham Lincoln (1809-1865)",
			"Man beherrscht die Menschen mit dem Kopf. Man kann nicht mit dem Herzen Schach spielen.\nNicolas Chamfort (1741-1794)",
			"Warum folgt man der Mehrheit? Etwa weil sie mehr Vernunft hat? Nein, weil sie mehr Macht hat.\nBlaise Pascal (1623-1662)",
			"Wen das Wort nicht schlägt, den schlägt auch der Stock nicht.\nPlato (um 400 v.Chr.)",
			"Wenn ein Mächtiger teilt, beträgt seine Hälfte mindestens sechzig Prozent.\nGeorges Clemenceau (1841-1929)",
			"Wenn es um die Macht geht, darf man keinem Menschen trauen, sondern muss alle Fesseln der Verfassung anlegen.\nThomas Jefferson (1743-1826)",
			"Wer die anderen neben sich klein macht ist nie groß.\nJohann Gottfried Seume (1763-1810)",
			"Wer stark ist, kann es sich erlauben, leise zu sprechen.\nTheodore Roosevelt (1858-1919)",
			"Willst du den Charakter eines Menschen erkennen, so gib ihm Macht.\nAbraham Lincoln (1809-1865)",
			"Alle guten Grundsätze sind in der Welt vorhanden, man braucht sie nur anzuwenden.\nBlaise Pascal (1623-1662)",
			"Auf je tausend, die an den Blättern des Bösen zupfen, kommt einer der an der Wurzel hackt.\nHenry David Thoreau (1817-1862)",
			"Besser ist es, hinkend auf dem rechten Wege zu gehen, als mit einem festen Schritt abseits.\nAugustinus von Hippo (um 400 n.Chr.)",
			"Denn der Mensch als Kreatur hat von Rücksicht keine Spur.\nWilhelm Busch (1832-1908)",
			"Der Aufbau von Feindbildern ist die wirksamste Methode zur Manipultion der Massen.\nThomas Pfitzer (geb. 1961)",
			"Der edle Mensch schämt sich, wenn seine Worte ständig großartiger sind als seine Taten.\nKonfuzius (551-479 v.Chr.)",
			"Der Gedanke macht die Würde des Menschen aus.\nBlaise Pascal (1623-1662)",
			"Der größte Lump bleibt obenauf.\nWilhelm Busch (1832-1908)",
			"Der Mensch lebt, um zu denken, und seine Pflicht ist es, gut zu denken: Der erste Grundsatz ist die Moral.\nBlaise Pascal (1623-1662)",
			"Die gewaltigsten Prediger sind die guten Werke.\nMeister Eckhart (um 1300)",
			"Die Großen schaffen das Große, die Guten das Dauernde.\nMarie von Ebner-Eschenbach (1830-1916)",
			"Die Rücksicht auf das Recht des anderen, das ist der Friede.\nBenito Juarez (1806-1872)",
			"Ehrlichkeit verschafft dir vielleicht nicht viele Freunde, dafür aber die Richtigen.\nSpanisches Sprichwort",
			"Ein gutes Gewissen ist ein ständiges Weihnachten.\nBenjamin Franklin (1706-1790)",
			"Ein gutes Gewissen ruft die Menschen zu Zeugen, ein schlechtes ist auch in der Einsamkeit angstvoll und unruhig.\nSeneca (um Christi Geburt)",
			"Ein jeder kriegt, ein jeder nimmt in dieser Welt, was ihm bestimmt.\nWilhelm Busch (1832-1908)",
			"Es genügt nicht, gute geistige Anlagen zu besitzen. Die Hauptsache ist, sie gut anzuwenden.\nRene Descartes (1596-1650)",
			"Es gibt viele Religionen, aber nur eine Moral.\nJohn Ruskin (1819-1900)",
			"Es lohnt sich schon, etwas Schweres auf sich zu nehmen, wenn man es einem Menschen damit leichter macht.\nStefan Zweig (1881-1942)",
			"Ich habe die Erfahrung gemacht, dass Leute ohne Laster auch sehr wenige Tugenden haben.\nAbraham Lincoln (1809-1865)",
			"Kapital hat keine Moral.\nSprichwort",
			"Kein Mensch auf der Erde hat das Recht, seine Kräfte ungebraucht zu lassen und durch fremde Kräfte zu leben.\nJohann Gottlieb Fichte (1762-1814)",
			"Nur der Ehrgeiz, durch den keine Eitelkeit schimmert, hat Zukunft.\nSully Prudhomme (1839-1907)",
			"Nur zwei Tugenden gibt's. O, wären sie immer vereinigt, immer die Güte auch groß, immer die Größe auch gut.\nFriedrich Schiller (1759-1805)",
			"Ohne Grundsätze ist der Mensch wie ein Schiff ohne Steuer und Kompass, das von jedem Winde hin und her getrieben wird.\nSamuel Smiles (1812-1904)",
			"Sag, was du zu sagen hast, und nicht, was du sagen solltest.\nHenry David Thoreau (1817-1862)",
			"Schneide einen Dieb vom Galgen und er bestiehlt dich.\nWilhelm Busch (1832-1908)",
			"Seine Pflicht erkennen und tun, das ist die Hauptsache.\nFriedrich der Große (1712-1786)",
			"Sei nicht zu moralisch, du bringst dich sonst um viele Lebenswerte.\nSei mehr als moralisch: Sei nicht bloß gut im allgemeinen, sondern sei zu etwas gut.\nHenry David Thoreau (1817-1862)",
			"Studiere die Menschen, nicht um sie zu überlisten und auszubeuten, sondern um das Gute in Ihnen aufzuwecken und in Bewegung zu setzen.\nGottfried Keller (1819-1890)",
			"Tue das, wodurch du würdig wirst, glücklich zu sein!\nImmanuel Kant (1724-1804)",
			"Unser Unterbewusstsein ist die wahre moralische Instanz.\nThomas Pfitzer (geb. 1961)",
			"Was für eine Philosophie man wähle, hängt davon ab, was für ein Mensch man ist.\nJohann Gottlieb Fichte (1762-1814)",
			"Wem genug zu wenig ist, dem ist nichts genug.\nEpikur von Samos (um 300 v.Chr.)",
			"Wer die Unglücklichen verteidigt, erzürnt die Glücklichen.\nVictor Hugo (1802-1885)",
			"Wer ist so fest, dass ihn nichts verführen kann.\nWilliam Shakespeare (1564-1616)",
			"Zur Wahrheit gehören immer zwei; einer der sie sagt, und einer der sie versteht.\nHenry David Thoreau (1817-1862)",
			"Am reichsten ist der Mensch, dessen Vergnügen am billigsten ist.\nHenry David Thoreau (1817-1862)",
			"Dem wachsenden Geld folgt die Sorge auf dem Fuße.\nHoraz (um Christi Geburt)",
			"Der größte Gefallen, den wir anderen tun können, ist nicht, mit ihnen unsern Reichtum zu teilen, sondern ihnen ihren eigenen zu offenbaren.\nHenry David Thoreau (1817-1862)",
			"Im Leben geht Zufriedenheit über Reichtum.\nJean-Baptiste Molière (1622-1673)",
			"Den Reichtum eines Menschen kann man an den Dingen messen, die er entbehren kann, ohne seine gute Laune zu verlieren.\nHenry David Thoreau (1817-1862)",
			"Der Besitz verschafft Freunde. Das gebe ich zu. Aber falsche. Und er verschafft sie nicht dir, sondern sich.\nErasmus von Rotterdam (1466-1536)",
			"Der eigentliche Sinn des Reichtums ist, freigebig davon zu spenden.\nBlaise Pascal (1623-1662)",
			"Der Mammon ist wie das Feuer, der nützlichste Diener, der furchtbarste Herr.\nThomas Carlyle (1795-1881)",
			"Der Mensch soll lieber nach Reichtum als nach Weisheit streben. Denn ich sehe die Weisen vor den Türen der Reichen, nicht aber die Reichen vor den Türen der Weisen.\nSimonides von Keos (556-468 v.Chr.)",
			"Die größten Reichtümer und Werte sind am weitesten davon entfernt, geschützt zu werden.\nHenry David Thoreau (1817-1862)",
			"Ertrags, es hat schon seinen Sinn, so wie es ist, gleichviel, ob wir ihn je erfahren oder nicht.\nRainer Maria Rilke (1875-1926)",
			"Erworbenes zu bewahren ist kein geringeres Verdienst als Erwerben.\nOvid (um Christi Geburt)",
			"Geizhälse sind die Plage ihrer Zeitgenossen, aber das Entzücken ihrer Erben.\nTheodor Fontane (1819-1898)",
			"Häufe jenen Reichtum auf, den Diebe nicht entwenden,\nTyrannen nicht ergreifen können, welcher dir in den Tod folgt,\nwelcher nicht schwindet und nicht verdirbt.\nIndische Weisheit",
			"Der ist reich, dessen Herz zufrieden ist.\nIndische Weisheit",
			"Kaum hat mal einer ein bisserl was, gleich gibt es welche, die ärgert das.\nWilhelm Busch (1832-1908)",
			"Lieber arm und gesund, als reich und krank.\nWeisheit",
			"Lieber neureich, als nie reich.\nSprichwort",
			"Mit den Begierden wachsen die Bedürfnisse und mit diesen die Abhängigkeit und Nöte.\nHeraklit (535-475 v.Chr.)",
			"Reich ist man nicht durch das, was man besitzt, sondern mehr noch durch das, was man mit Würde zu entbehren weiß.\nEpikur von Samos (um 300 v.Chr.)",
			"Reich ist, wer viel hat, reicher ist, wer wenig braucht, am reichsten ist, wer viel gibt.\nGerhard Tersteegen (1697-1769)",
			"Unter allen Besitzungen auf Erden ist die, ein Herz zu haben, die kostbarste.\nJohann Wolfgang von Goethe (1749-1832)",
			"Wer gut wirtschaften will, sollte nur die Hälfte seiner Einnahmen ausgeben, wenn er reich werden will, sogar nur ein Drittel.\nFrancis Bacon (1561-1626)",
			"Wer seine Wünsche zähmt, ist immer reich genug.\nVoltaire (1694-1778)",
			"Zum Reichtum führen viele Wege, und die meisten von ihnen sind schmutzig.\nCicero (106-43 v.Chr.)",
			"Zwei werden nicht satt: Wer Wissen und wer Reichtum sammelt.\nArabisches Sprichwort ",
			"Es gibt ebensowenig hundertprozentige Wahrheit wie hundertprozentigen Alkohol.\nSigmund Freud (1856-1939)",
			"Alles was du sagst, sollte wahr sein. Aber nicht alles was wahr ist, solltest du auch sagen.\nVoltaire (1694-1778)",
			"Einer neuen Wahrheit ist nichts schädlicher als ein alter Irrtum.\nJohann Wolfgang von Goethe (1749-1832)",
			"Die Wahrheit ist zu schlau, um gefangen zu werden.\nWilhelm Busch (1832-1908)",
			"Die Wahrheit kann auch eine Keule sein, mit der man andere erschlägt.\nAnatole France (1844-1924)",
			"Gewohnheit, Sitte und Brauch sind störker als die Wahrheit.\nVoltaire (1694-1778)",
			"In der Mitte liegt die Wahrheit? Keineswegs. Nur in der Tiefe.\nArthur Schnitzler (1862-1931)",
			"Man kann alles begründen, selbst die Wahrheit.\nOscar Wilde (1854-1900)",
			"Nur die Lüge braucht die Stütze der Staatsgewalt, die Wahrheit steht von alleine aufrecht.\nBenjamin Franklin (1706-1790)",
			"Nur in der Stille kann die Wahrheit Früchte ansetzen und Wurzeln schlagen.\nAntoine de Saint Exupery (1900-1944)",
			"Zwei Wahrheiten können sich nie widersprechen.\nGalileo Galilei (1564-1642)",
			"Unanfechtbare Wahrheiten gibt es überhaupt nicht, und wenn es welche gäbe, wären sie langweilig.\nTheodor Fontane (1819-1898)",
			"Wahre Worte sind nicht immer schön. Schöne Worte sind nicht immer wahr.\nLaotse (um 550 v.Chr.)",
			"Wahrheit, die man behalten will, muss man recht oft ausgeben.\nFranz von Schönthan (1849-1913)",
			"Wahrheit ist eine widerliche Arznei, man bleibt lieber krank, ehe man sich entschließt, sie einzunehmen.\nAugust von Kotzebue (1761-1819)",
			"Wahrheit schlägt immer eine Bresche, Lüge schlägt immer in Trümmer.\nGeorge Sand (1804-1876)",
			"Wenn du das Unmögliche ausgeschlossen hast, dann ist das, was übrig bleibt, die Wahrheit, wie unwahrscheinlich sie auch ist.\nArthur Conan Doyle (1859-1930)",
			"Wer die Bettdecke von der schlummernden Wahrheit wegzieht, den nennt man einen Ruhestörer.\nLudwig Börne (1786-1837)",
			"Wer einmal lügt dem glaubt man nicht, auch wenn er mal die Wahrheit spricht.\nSprichwort",
			"Wir suchen die Wahrheit, finden wollen wir sie aber nur dort, wo es uns beliebt.\nMarie von Ebner-Eschenbach (1830-1916)",
			"Zur Wahrheit gehören immer zwei: Einer, der sie ausspricht, und einer, der sie versteht.\nHenry David Thoreau (1817-1862)",
			"Die Hand die gibt steht über der Hand die nimmt. Geld kennt keine Loyalität. Finanziers sind ohne Patriotismus und ohne Anstand. Ihr einziges Ziel ist der Gewinn.\nNapoleon Bonaparte (1769-1821)",
			"Die Klage über die Schärfe des Wettbewerbs ist in Wirklichkeit meist nur eine Klage über den Mangel an Einfällen.\nWalter Rathenau (1867-1922)",
			"Man muss ernten, wenn die Bäume Früchte tragen.",
			"Solange billiges Öl sprudelt, solange läuft die Wirtschaft auch rund.\nSprichwort",
			"Es gibt kaum etwas auf dieser Welt, das nicht irgendjemand ein wenig schlechter machen und etwas billiger verkaufen könnte.\nJohn Ruskin (1819-1900)",
			"Für augenblicklichen Gewinn verkaufe ich die Zukunft nicht.\nWerner von Siemens (1816-1892)",
			"Ich stehe Statistiken etwas skeptisch gegenüber. Denn laut Statistik haben ein Millionär und ein armer Kerl jeder eine halbe Million.\nFranklin D. Roosevelt (1882-1945)",
			"Jede Wirtschaft beruht auf dem Kredit-System, das heißt auf der irrtümlichen Annahme, der andere werde das geborgte Geld zurückzahlen.\nKurt Tucholsky (1890-1935)",
			"Nicht die Politik ist das Schicksal, sondern die Wirtschaft.\nWalther Rathenau (1867-1922)",
			"Eine Krähe hackt der anderen kein Auge aus.\nSprichwort",
			"Sind Rüben auf dem Markt gefragt, muss man sie nicht waschen.\nChinesisches Sprichwort",
			"Steuern sind ein erlaubter Fall von Raub.\nThomas von Aquin (1224-1274)",
			"Was die Weltwirtschaft angeht, so ist sie verflochten.\nKurt Tucholsky (1890-1935)",
			"Wenn man einen Sumpf trocken legen will, darf man nicht die Frösche beauftragen.\nMark Twain (1835-1910)",
			"Wer einen Tiger reitet, kann nicht mehr absteigen.\nChinesisches Sprichwort",
			"Wer gut wirtschaften will, sollte nur die Hälfte seiner Einnahmen ausgeben, wenn er reich werden will, sogar nur ein Drittel.\nFrancis Bacon (1561-1626)",
			"Wer nur um Gewinn kämpft, erntet nichts, wofür es sich lohnt zu leben.\nAntoine de Saint-Exupery (1900-1944)",
			"Wirtschaftskrisen erkennt man daran, dass die Bevölkerung aufgefordert wird, Vertrauen zu haben.\nKurt Tucholsky (1890-1935) ",
			"Alles verzehrt die Macht der Zeiten.\nSophokles (um 450 v.Chr.)",
			"Angenehm ist am Gegenwärtigen die Tätigkeit, am Künftigen die Hoffnung und am Vergangenen die Erinnerung.\nAristoteles (384-322 v.Chr.)",
			"Das meiste haben wir gewöhnlich in der Zeit getan, in der wir meinten, zu wenig zu tun.\nMarie von Ebner-Eschenbach (1830-1916)",
			"Denke immer daran, dass es nur eine wichtige Zeit gibt: Heute, hier, jetzt.\nLeo Tolstoi (1828-1910) ",
			"Begegnen wir der Zeit, wie sie uns sucht.\nMond teilweise von Wolken verdeckt\nMonde und Jahre vergehen,\naber ein schöner Moment leuchtet das Leben hindurch. (Franz Grillparzer 1791-1872)\nWilliam Shakespeare (1565-1616)",
			"Die Ehrfurcht vor der Vergangenheit und die Verantwortung gegenüber der Zukunft geben fürs Leben die richtige Haltung.\nDietrich Bonhoeffer (1906-1945)",
			"Die Gegenwart ist die einzige Zeit, die uns wirklich gehört.\nBlaise Pascal (1623-1662)",
			"Die Zeiten ändern sich, und wir mit mit ihnen.\nOvid (um Christi Geburt)",
			"Die Zeit, die alte Bügelfrau, macht alles wieder schlicht.\nWilhelm Busch (1832-1908)",
			"Die Zeit ist eine große Meisterin, sie ordnet viele Dinge.\nPierre Corneille (1606-1684)",
			"Die Zeit ist nur ein leerer Raum, dem Begebenheiten, Gedanken und Empfindungen erst Inhalt geben.\nWilhelm von Humboldt (1767-1835)",
			"Die Zeit ist schlecht? Wohlan. Du bist da, sie besser zu machen.\nThomas Carlyle (1795-1881)",
			"Die Zeit ist, wie jede Zeit, eine sehr gute Zeit, wenn wir nur etwas mit ihr anzufangen wissen.\nRalph Waldo Emerson (1803-1882)",
			"Die Zeit verweilt lange genug für denjenigen, der sie nutzen will.\nLeonardo da Vinci (1452-1519)",
			"Die zwei größten Tyrannen der Erde: Der Zufall und die Zeit.\nJohann Gottfried von Herder (1744-1803)",
			"Kommt Zeit, kommt Rat.\nSprichwort",
			"Er kam prinzipiell zu spät, da sein Grundsatz lautete, Pünktlichkeit stehle einem die Zeit.\nOscar Wilde (1854-1900)",
			"Es gibt Diebe, die von den Gesetzen nicht bestraft werden und doch dem Menschen das Kostbarste stehlen: Die Zeit.\nNapoleon Bonaparte (1769-1821)",
			"Es ist nicht zu wenig Zeit, die wir haben, sondern es ist zu viel Zeit, die wir nicht nutzen.\nSeneca (um Christi Geburt)",
			"Gäbe es die letzte Minute nicht, so würde nie etwas fertig.\nMark Twain (1835-1910)",
			"Gebraucht der Zeit, sie geht so schnell von hinnen, doch Ordnung lehrt euch Zeit gewinnen!\nJohann Wolfgang von Goethe (1749-1832)",
			"Gegen den Strom der Zeit kann zwar der einzelne nicht schwimmen, aber wer Kraft hat, hält sich und lässt sich von demselben nicht mit fortreißen.\nJohann Gottfried Seume (1763-1810)",
			"Gestern war heute noch morgen.\nEin wahrer Spruch und ein Album der Böhse Onkelz",
			"Gut Ding will Weile haben.\nSprichwort",
			"Nimmst du dir nicht die Zeit, dich um deine Krankheit zu kümmern, hast du genug Zeit zu sterben.\nAfrikanisches Sprichwort",
			"Wenn die Zeit kommt, in der man könnte, ist die vorüber, in der man kann.\nMarie von Ebner-Eschenbach (1830-1916)",
			"Zeit ist nichts als der Strom, in den ich zum Fischen steige.\nHenry David Thoreau (1817-1862) ",
			"Alles freut sich und hoffet, wenn der Frühling sich erneut.\nFriedrich Schiller (1759-1805)",
			"Betrachtet das Erwachen des Frühlings und das Erscheinen der Morgenröte! Die Schönheit offenbart sich denjenigen, die betrachten.\nKhalil Gibran (1883-1931)",
			"Die Bäume fahren im Frühling aus der Haut.\nWilhelm Busch (1832-1908)",
			"Das Schöne am Frühling ist, dass er immer gerade dann kommt, wenn man ihn am dringendsten braucht.\nJean Paul (1763-1825)",
			"Im Frühling kehrt die Wärme in die Knochen zurück.\nVergil (um 50 v.Chr.)",
			"Der Frühling ist eine echte Auferstehung, ein Stück Unsterblichkeit.\nHenry David Thoreau (1817-1862)",
			"Die Blumen des Frühlings sind die Träume des Winters.\nKhalil Gibran (1883-1931)",
			"Ein jeder Frühling ist neue Feier der großen Vereinigung der männlichen und weiblichen Natur.\nJoseph Görres (1776-1848)",
			"Frühling ist die schöne Jahreszeit, in der der Winterschlaf aufhört und die Frühjahrsmüdigkeit beginnt.\nEmanuel Geibel (1815-1884) ",
			"Der Frühling ist die Zeit der Pläne, der Vorsätze.\nLew Nikolajewitsch Tolstoi (1828-1910) ",
			"Schöner Frühling, komm doch wieder, lieber Frühling, komm doch bald!\nBring uns Blumen, Laub und Lieder, schmücke wieder Feld und Wald!\nHoffmann von Fallersleben (1798-1874)",
			"Vom Eise befreit sind Strom und Bäche; durch des Frühlings holden belebenden Blick.\nJohann Wolfgang von Goethe (1749-1832)",
			"Was der Frühling nicht säte, kann der Sommer nicht reifen, der Herbst nicht ernten, der Winter nicht genießen.\nJohann Gottfried Herder (1744-1803)",
			"Wenn der Frühling ins Land zieht, wäre es eine Beleidigung der Natur, nicht einzustimmen in ihr Jauchzen.\nJohn Milton (1608-1674)",
			"Wollte man die Herrlichkeit des Frühlings und seiner Blüten nach dem wenigen Obst berechnen, das zuletzt noch von den Bäumen genommen wird, so würde man eine sehr unvollkommene Vorstellung jener lieblichen Jahreszeit haben.\nJohann Wolfgang von Goethe (1749-1832)",
			"An seinen Vorfahren kann man nichts ändern, aber man kann mitbestimmen, was aus den Nachkommen wird.\nFrancois de la Rochefoucauld (1613-1680)",
			"Der hat am besten für die Zukunft gesorgt, der für die Gegenwart sorgt.\nFranz Kafka (1883-1924)",
			"Die eine Generation baut die Straße, auf der die nächste fährt.\nChinesische Weisheit",
			"Die Geburtswehen der Zukunft gehören zu den Visionen des Philosophen.\nVictor Hugo (1802-1885)",
			"Die Lebenskraft eines Zeitalters liegt nicht in seiner Ernte, sondern in seiner Aussaat.\nLudwig Börne (1786-1837)",
			"Die Vergangenheit und die Gegenwart sind unsere Mittel. Die Zukunft allein ist unser Zweck.\nBlaise Pascal (1623-1662)",
			"Die Zukunft hat viele Namen.\nFür die Schwachen ist sie das Unerreichbare.\nFür die Furchtsamen ist sie das Unbekannte.\nFür die Tapferen ist sie die Chance.\nVictor Hugo (1802-1885)",
			"Die Zukunft ist eine undankbare Person, die gerade nur die quält, die sich recht sorgsam um sie bekümmern.\nJohann Nepomuk Nestroy (1801-1862)",
			"Die Zukunft soll man nicht voraussehen wollen, sondern möglich machen.\nAntoine de Saint-Exupery (1900-1944)",
			"Für einen Vater, dessen Kind stirbt, stirbt die Zukunft. Für ein Kind, dessen Eltern sterben, stirbt die Vergangenheit.\nBerthold Auerbach (1812-1882)",
			"Gegen die Zukunft kann man nicht kämpfen. Die Zeit ist auf ihrer Seite.\nWilliam Ewart Gladstone (1809-1898)",
			"Ich bin Pessimist für die Gegenwart, aber Optimist für die Zukunft.\nWilhelm Busch (1832-1908)",
			"In dem Heute wandelt schon das Morgen.\nFriedrich von Schiller (1759-1805)",
			"Was ist die Zukunft? Nichts als du selbst.\nErnst von Feuchtersleben (1806-1849)",
			"Man muss die Zukunft abwarten und die Gegenwart genießen oder ertragen.\nWilhelm von Humboldt (1767-1835)",
			"Man muß die Zukunft im Sinn haben und die Vergangenheit in den Akten.\nCharles-Maurice de Talleyrand-Perigord (1754-1838)",
			"Man muss nicht darauf sehen, woher die Dinge kommen, sondern wohin sie gehen.\nSeneca (um Christi Geburt)",
			"Nicht in die ferne Zeit verliere dich. Den Augenblick ergreife. Der ist dein.\nFriedrich Schiller (1759-1805)",
			"Prognosen sind schwierig, besonders wenn sie die Zukunft betreffen.\nMark Twain (1835-1910)",
			"Unsere Bestimmung verfügt über uns, auch wenn wir sie noch nicht kennen, es ist die Zukunft, die unserem Heute die Regel gibt.\nFriedrich Nietzsche (1844-1900)",
			"Was die Zukunft betrifft, so ist deine Aufgabe nicht, sie vorauszusehen, sondern sie zu ermöglichen\nAntoine de Saint-Exupéry (1900-1944)",
			"Was heute Utopie ist, wird morgen von Fleisch und Blut sein.\nVictor Hugo (1802-1885)",
			"Was wir heute tun, entscheidet darüber, wie die Welt morgen aussieht.\nMarie von Ebner-Eschenbach (1830-1916)",
			"Wenn der Mensch nicht über das nachdenkt, was in ferner Zukunft liegt, wird er das schon in naher Zukunft bereuen.\nKonfuzius (551-479 v.Chr.)",
			"Wenn die Zeit kommt, in der man könnte, ist die vorüber, in der man kann.\nMarie von Ebner-Eschenbach (1830-1916)",
			"Wer das Morgen nicht bedenkt, wird Kummer haben, bevor das Heute zu Ende geht.\nKonfuzius (um 500 v.Chr.)",
			"Zukunft ist ein Kind der Gegenwart.\nChristoph August Tiedge (1752-1841)",
			"Zukunft ist jene Zeit, in der unsere Geschäfte gut gehen, unsere Freunde treu sind und unser Glück gesichert ist.\nAmbrose Bierce (1842-1914)",
			"Zur Wahrscheinlichkeit gehört auch, dass das Unwahrscheinliche eintreten kann!\nAristoteles (384-322 v.Chr.)",
			"Alles geben, außer auf!", "Alles ist gut, wenn es aus Schokolade ist.",
			"Bin gerade außer mir, aber in 5 Minuten wieder da.", "Bin grad die Welt retten.",
			"Bin krank. Hab Fußballfieber!", "Bitte nicht stören. Gestört bin ich schon genug.",
			"Das wird mir hier alles zu kindisch. Komm Teddy, wir gehn!",
			"Heimat ist dort, wo sich mein Handy automatisch mit einem WLAN verbindet.",
			"Hier könnte ihre Werbung stehen.", "Hoppla, sprach der Igel und stieg von der Klobürste.",
			"Ich bin nicht klein. Ich bin einfach nur auf das Beste reduziert.",
			"Ich bin schizophren und mir geht es allen gut.",
			"Ich bin verdammt gut im Bett. Manchmal kann ich 10 Stunden am Stück schlafen.",
			"Ich bin wach. Das muss im Moment reichen.", "Ich liebe Freitag.",
			"Ist das Leben noch so trübe, hoch die Rübe!", "Ich bin der Boss, basta.",
			"Mit nur 10% das Haus verlassen. Man muss doch auch mal was riskieren im Leben!",
			"Prüfungen kann man wiederholen, Partys nicht.", "Reden ist Silber, Ausreden sind Gold!",
			"Sonntag bin ich ein Mofa. Halb Mensch, halb Sofa!",
			"Stehe mit beiden Beinen fest auf der Leitung.", "Versaut bin ich nicht, eher moralisch flexibel.",
			"Wer Schmetterlinge lachen hört, der weiß wie Wolken schmecken.\n1879-1955, dt.-amerik. Physiker (Relativitätstheorie), 1921 Nobelpreis	",
			"Wenn eine Idee am Anfang nicht absurd klingt, dann gibt es keine Hoffnung für sie.\nAlbert Einstein (1879-1955), dt.-amerik. Physiker (Relativitätstheorie), 1921 Nobelpr.	",
			"Der Sinn des Lebens besteht nicht darin ein erfolgreicher Mensch zu sein, sondern ein wertvoller.\nAlbert Einstein (1879-1955)",
			"Wenn's alte Jahr erfolgreich war, dann freue dich aufs neue. Und war es schlecht, ja dann erst recht.\nAlbert Einstein (1879-1955)",
			"Die größte Erfindung des menschlichen Geistes? - Die Zinseszinsen!\nAlbert Einstein (1879-1955)",
			"Wer sich nicht mehr wundern kann, der ist seelisch bereits tot.\nAlbert Einstein (1879-1955)",
			"Wenn ich die Folgen geahnt hätte, wäre ich Uhrmacher geworden.\nAlbert Einstein (1879-1955)",
			"Warum bringt uns die angewandte Wissenschaft, die Arbeit erspart und das Leben leichter macht, so wenig Freude? Die simple Antwort lautet: weil wir noch nicht gelernt haben, sinnvollen Gebrauch davon zu machen. (Why does this magnificent applied science which saves work && makes life easier bring us so little happiness? The simple answer runs: Because we have not yet learned to make sensible use of it.)\nAlbert Einstein (1879-1955)",
			"Der Fortgang der wissenschaftlichen Entwicklung ist im Endeffekt eine ständige Flucht vor dem Staunen.\nAlbert Einstein (1879-1955)",
			"Das Schönste, was wir entdecken können, ist das Geheimnisvolle.\nAlbert Einstein (1879-1955)",
			"Es ist schwieriger, eine vorgefaßte Meinung zu zertrümmern als ein Atom.\nAlbert Einstein (1879-1955)",
			"Universitäten sind schöne Misthaufen, auf denen gelegentlich einmal eine edle Pflanze gedeiht.\nAlbert Einstein (1879-1955)",
			"Nichts in der Welt ist so gefürchtet wie der Einfluß von Männern, die geistig unabhängig sind.\nAlbert Einstein (1879-1955)",
			"Meine Arbeit ist getan.\nAlbert Einstein (1879-1955)",
			"Zähmen sollen sich die Menschen, die sich gedankenlos der Wunder der Wissenschaft und Technik bedienen und nicht mehr davon geistig erfaßt haben als die Kuh von der Botanik der Pflanzen, die sie mit Wohlbehagen frißt.\nAlbert Einstein (1879-1955)",
			"Warum beglückt uns die herrliche, das Leben erleichternde, Arbeit ersparende Technik so wenig? Die einfache Antwort lautet: weil wir noch nicht gelernt haben, einen vernünftigen Gebrauch von ihr zu machen. Im Kriege dient sie dazu, daß wir uns gegenseitig verstümmeln. Im Frieden hat sie unser Leben hastig und unsicher gestaltet. Statt uns weitgehend von geisttötender Arbeit zu befreien, hat sie die Menschen zu Sklaven der Maschine gemacht, die meist mit Unlust ihr eintöniges, langes Tagewerk vollbringen und stets um ihr armseliges Brot zittern müssen.\nAlbert Einstein (1879-1955)",
			"Denkt auch daran, daß die Techniker es sind, die erst wahre Demokratie möglich machen. Denn sie erleichtern nicht nur des Menschen Tagewerk, sondern machen auch die Werke der feinsten Denker und Künstler, deren Genuß noch vor kurzem ein Privileg bevorzugter Klassen war, jedem zugänglich.\nAlbert Einstein (1879-1955)",
			"Am schwersten auf der Welt zu verstehen ist die Einkommensteuer.\nAlbert Einstein (1879-1955)",
			"Um ein tadelloses Mitglied einer Schafherde zu sein, muß man vom allem ein Schaf sein.\nAlbert Einstein (1879-1955)",
			"Die Rüstungsindustrie ist eine der größten Gefährdungen der Menschheit.\nAlbert Einstein (1879-1955)",
			"Wenn ein Mann eine Stunde mit einem hübschen Mädchen zusammensitzt, kommt ihm die Zeit wie eine Minute vor. Sitzt er dagegen auf einem heißen Ofen, scheint ihm schon eine Minute länger zu dauern als jede Stunde. Das ist Relativität.\nAlbert Einstein (1879-1955)",
			"Wenn die Menschen nur über das sprächen, was sie begreifen, dann würde es sehr still auf der Welt sein.\nAlbert Einstein (1879-1955)",
			"Jedes Denken wird dadurch gefärdert, daß es in einem bestimmten Augenblick sich nicht mehr mit Erdachtem abgeben darf, sondern durch die Wirklichkeit hindurch muß.\nAlbert Einstein (1879-1955)",
			"Phantasie ist wichtiger als Wissen. Wissen ist begrenzt, Phantasie aber umfaßt die ganze Welt.\nAlbert Einstein (1879-1955)",
			"Persönlichkeiten werden nicht durch schöne Reden geformt, sondern durch Arbeit und eigene Leistung.\nAlbert Einstein (1879-1955)",
			"Die Naturwissenschaft ohne Religion ist lahm, die Religion ohne Naturwissenschaft aber ist blind.\nAlbert Einstein (1879-1955)",
			"Wenige sind imstande, von den Vorurteilen der Umgebung abweichende Meinungen gelassen auszusprechen; die meisten sind sogar unfähig, überhaupt zu solchen Meinungen zu gelangen.\nAlbert Einstein (1879-1955)",
			"Ein Abend, an dem sich alle Anwesenden völlig einig sind, ist ein verlorener Abend.\nAlbert Einstein (1879-1955)",
			"Der übernächste Krieg wird nur noch mit Pfeil und Bogen entschieden.\nAlbert Einstein (1879-1955)",
			"So einfach wie möglich. Aber nicht einfacher!\nAlbert Einstein (1879-1955)",
			"Es gibt keine großen Entdeckungen und Fortschritte, solange es noch ein unglückliches Kind auf Erden gibt.\nAlbert Einstein (1879-1955)",
			"Das eigentlich Wertvolle ist im Grunde die Intuition. (The intellect has little to do on the road to discovery. There comes a leap in consciousness, call it intuition || what you will, && the solution comes to you && you don't know how || why.)\nAlbert Einstein (1879-1955)",
			"Als das eigentlich Wertvolle im menschlichen Getriebe empfinde ich nicht den Staat, sondern das schöpferische und fühlende Individuum, die Persönlichkeit: sie allein schafft das Edle und Sublime.\nAlbert Einstein (1879-1955)",
			"Das Problem ist heute nicht die Atomenergie, sondern das Herz des Menschen.\nAlbert Einstein (1879-1955)",
			"Der Fortschritt geschieht heute so schnell, dass, während jemand eine Sache für gänzlich undurchführbar erklärt, er von einem anderen unterbrochen wird, der sie schon realisiert hat.\nAlbert Einstein (1879-1955)",
			"Keine noch so große Zahl von Experimenten kann beweisen, daß ich recht habe; ein einziges Experiment kann beweisen, daß ich unrecht habe. (No amount of experimentation can ever prove me right; a single experiment can prove me wrong.)\nAlbert Einstein (1879-1955)",
			"Ein leerer Magen ist ein schlechter Ratgeber.\nAlbert Einstein (1879-1955)",
			"Es gibt keine andere vernünftige Erziehung, als Vorbild sein, wenn es nicht anders geht, ein abschreckendes.\nAlbert Einstein (1879-1955)",
			"Zwei Dinge sind unendlich: das Universum und die menschliche Dummheit; aber bei dem Universum bin ich mir noch nicht ganz sicher.\nAlbert Einstein (1879-1955)",
			"Solange man jung ist, gehören alle Gedanken der Liebe - später gehört alle Liebe den Gedanken.\nAlbert Einstein (1879-1955)",
			"Backe, backe, Kuchen,\nder Bäcker hat gerufen!\nWer will guten Kuchen backen,\nder muss haben sieben Sachen:\nEier und Schmalz, Butter und Salz,\nMilch und Mehl, Safran macht den Kuchen gehl!\nSchieb, schieb in'n Ofen 'nein.",
			"Das ist der Daumen,\nder schüttelt die Pflaumen,\nder hebt sie auf,\nder trägt sie nach Haus,\nder kleine Schelm isst sie alle auf.",
			"Meine Mutter schickt mich her,\nob der Kaffee fertig wär?\nSag ein schönes Kompliment und der Kaffee ist verbrennt.\nDie Milch ist aus dem Topf gelaufen,\nwas drin blieb, tut die Katze saufen.\nUnd wer nicht ist mit fortgeschwommen,\nder soll schnell zum Kaffee kommen!",
			"Heile, heile Segen\nsieben Tage Regen,\nsieben Tage Sonnenschein,\nwird alles wieder heile sein.\nHeile, heile Segen,\nsieben Tage Regen,\nsieben Tage Schnee,\ntut dem Kind schon nicht mehr weh.",
			"Bienchen, Bienchen,\nsumm' summ summ,\num die Blumen summ herum!\nTauch hinein dein Köpfchen,\nhol die Nektartröpfchen,\nflieg zurück zum Bienenhaus,\nmach uns süßen Honig draus",
			"Zehn kleine Zappelmänner zappeln hin und her,\nzehn kleinen Zappelmännern fällt das gar nicht schwer.\nZehn kleine Zappelmänner zappeln auf und nieder,\nzehn kleine Zappelmänner tun das immer wieder.\nZehn kleine Zappelmänner zappeln ringsherum,\nzehn kleine Zappelmänner, die sind gar nicht dumm.\nZehn kleine Zappelmänner spielen gern Versteck,\nzehn kleine Zappelmänner sind auf einmal weg.\nZehn kleine Zappelmänner sind nun wieder da,\nzehn kleine Zappelmänner rufen laut: Hurra!",
			"Hoppe, hoppe, Reiter,\nwenn er fällt dann schreit er.\nFällt er in den Graben,\nfressen ihn die Raben.\nFällt er in die Hecken,\nfressen ihn die Schnecken.\nFällt er in den Sumpf, macht der Reiter: plumps!",
			"Sitzt ein Büblein auf dem Ast ,\nhüpft von Ast zum Ästchen ,\nkuckt ins Vogelnestchen,\nei da lacht es, ei da kracht es,\nBlums da liegt es unten.",
			"Ein Huhn, das fraß,\nman glaubt es kaum,\ndie Blätter von 'nem Gummibaum,\ndann ging es in den Hühnerstall\nund legte einen Gummiball!",
			"Da hast 'nen Taler\ngeh auf den Markt,\nkauf dir 'ne Kuh\nund ein Kälbchen dazu\nDas Kälbchen hat ein Schwänzchen,\ndideldidel-dänzchen.",
			"Morgens früh um sechs\nkommt die kleine Hex'.\nMorgens früh um sieben\nkocht sie gelbe Rüben.\nMorgens früh um acht\nwird Kaffee gemacht.\nMorgens früh um neun\ngeht sie in die Scheun'.\nMorgens früh um zehn\nholt sie Holz und Spän',\nfeuert an um elf,\nkocht dann bis um zwölf:",
			"Fröschebein' und Krebs und Fisch.\nHurtig, Kinder, kommt zu Tisch!",
			"Eine kleine Dickmadam\nfuhr mal mit der Eisenbahn.\nDickmadam, die lachte,\nEisenbahn, die krachte.Eins, zwei, drei,\nund du bist frei!",
			"Piep, Piep, Piep,\nwir haben uns alle lieb,\nein jeder esse was er kann,\nnur nicht seinen Nebenmann;\nund nimmt man's ganz genau,auch nicht seine Nebenfrau;\nund auch nicht das Geschirr,\nsonst macht's im Magen Klirr.",
			"Es war einmal ein Mann,\nder hatte sieben Kinder,\ndie Kinder sprachen:\nVater erzähl' uns eine Geschichte.\nDa fing der Vater an,\nes war einmal ein Mann,\nder hatte sieben Kinder",
			"Messer, Gabel, Schere, Licht\nsind für kleine Kinder nicht",
			"Advent, Advent\nein Lichtlein brennt\nerst eins dann zwei\ndann drei dann vier\ndann steht das Christkind vor der Tür\nund wenn die fünfte Kerze pennt\ndann hast du Weihnachten verpennt",
			"Abends wenn ich schlafen geh\nvierzehn Engel um mich stehn\nzwei zu meiner Rechten\nzwei zu meiner Linken\nzwei zu meinen Häupten\nzwei zu meinen Füßen\nzwei, die mich decken\nzwei, die mich wecken\nzwei, die mich führen\nins himmlische Paradies\n(Amen)",
			"Ich bin klein\nmein Herz ist rein\ndaß niemand drin wohne\nals Jesus allein",
			"Piep, Piep, Mäuschen,\nbleib in Deinem Häuschen.\nFrisst du mir mein Butterbrot,\nkommt die Katz' und beißt dich tot.\nPiep, Piep, Piep, recht guten Appetit",
			"Wir haben Hunger, Hunger, Hunger, haben Hunger, Hunger, Hunger,\nhaben Hunger, Hunger, Hunger, haben Durst.\nWo bleibt das Essen, Essen, Essen,\nbleibt das Essen, Essen, Essen, bleibt die Wurst?\nWenn wir nichts kriegen, kriegen, kriegen,\nfressen wir Fliegen, Fliegen, Fliegen,\nfressen wir Fliegen, Fliegen, Fliegen,\nvon der Wand."));
	
	@Override
	public void start(Stage primaryStage) {
		try {

			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Hauptfenster.fxml"));
			Parent root = null;
			try {
				root = (Parent) fxmlLoader.load();
			} catch (IOException e) {
				e.printStackTrace();
				DialogBoxes.showErrorBox("Error", "Ein Fehler ist aufgetreten!", e.getMessage());
			}
			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.initStyle(StageStyle.DECORATED);
			stage.setTitle("Nachdenkliche Texte");

			Scene scene = new Scene(root);
			
			//properly close application (if this doesn't happen automatically)
			stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			    @Override
			    public void handle(WindowEvent t) {
			        Platform.exit();
			        System.exit(0);
			    }
			});

			stage.setResizable(false);
			stage.setScene(scene);
			stage.getIcons().add(new Image(getClass().getResourceAsStream("/favicon.ico")));
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
			DialogBoxes.showErrorBox("Error", "Ein Fehler ist aufgetreten!", e.getMessage());
		}
	}
	
	@FXML
	public void keyInput(KeyEvent key){

			String code = String.valueOf(key.getCode());

			switch (code) {
			case "Q":
				zufaellig();
				break;
			case "W":
				uebersetzen();
				break;
			case "E":
				saveAsPng();
				break;
			case "R": 
				zufaellig();
				uebersetzen();
				zufaelligDrehen();
				saveAsPng();
				break;
			case "A":
				addrandomColor();
				break;
			case "S":
				zufaelligDrehen();
				break;
			case "D":
				geradeDrehen();
				break;
			case "F":
				addrandomPic();
				break;
			case "T":
				hundertXuebersetzen();
				break;
			case "Y":
				entferneParagraph();
				break;
			case "L":
				clear();
				break;
			case "G":
				loadText();
				break;
			case "H":
				loadPic();
				break;
			default:
				//do nothing
			}
			key.consume();

	}
	
	public static void main(String[] args) {
		launch(args);
	}

	@FXML
	private void initialize() {
		textLabel.setVisible(false);
	}

	@FXML
	public void uebersetzen() {
		try {
			String OPtext = TextFeld.getText();
			if (OPtext == null || OPtext.isEmpty())
				TextFeld.setText("Kopiere bitte einen Text in dieses Feld hinein");
			else {
				String translatedString = OPtext;
				if(translateBox.isSelected())
					translatedString = startTranslate(OPtext);
				TextFeld.setText(translatedString);
				switchText(translatedString);
			}
		} catch (Exception e) {
			e.printStackTrace();
			DialogBoxes.showErrorBox("Error", "Ein Fehler ist aufgetreten!", e.getMessage());
		}
	}

	@FXML
	public void hundertXuebersetzen() {
		if (UebersetzButton.getText().equals("Neu"))
			switchText("");
		try {
			String OPtext = TextFeld.getText();
			if (OPtext == null || OPtext.isEmpty())
				TextFeld.setText("Kopiere bitte einen Text in dieses Feld hinein");
			else {
				String translatedString = OPtext;

				for (int i = 0; i < 100; i++)
					translatedString = startTranslate(translatedString);

				TextFeld.setText(translatedString);
				switchText(translatedString);
			}
		} catch (Exception e) {
			e.printStackTrace();
			DialogBoxes.showErrorBox("Error", "Ein Fehler ist aufgetreten!", e.getMessage());
		}
	}

	// verwendet von Übersetzen Button und 100x Uebersetzen:
	public String startTranslate(String OPtext) {
		// Uebersetzen:
		String newText = OPtext
				.replace("ein", "1").replace("zwei", "2")
				.replace("drei", "3").replace("vier", "4").replace("fünf", "5").replace("sechs", "6")
				.replace("sieben", "7").replace("acht", "8").replace("neun", "9").replace("zehn", "10")
				.replace("v.Chr.", "v Chr ").replace("v. Chr.", "v Chr ").replace("n.Chr.", "n Chr ")
				.replace("n. Chr.", "n Chr ");
		String[] newTextArray = newText.split(" ");
		String translatedString = "";
		for (String word : newTextArray) {
			translatedString = translatedString + " " + translateWords(word);
		}
		translatedString = translatedString
				.replace("&#128526; &#128526;", "&#128526;").replace("lol &#128526;", "lol")
				.replace("&#128526; lol", "lol").replace("lol lol", "lol").replace(" dy ", " das ")
				.replace(" wy ", " was ").replace(" sy ", " sie ").replace(" ny ", " nie ").replace(" Dy ", " Das ")
				.replace(" Wy ", " Was ").replace(" Sy ", " Sie ").replace(" Ny ", " Nie ").replace("schsch", "sch");
		if ((new Random().nextInt(100 - 0 + 1) + 0) < 1)
			translatedString = translatedString.toUpperCase();

		// Leerzeichen am Anfang entfernen:
		while (translatedString.startsWith(" "))
			translatedString = translatedString.substring(1);

		return translatedString;
	}

	@FXML
	public void zufaellig() {
		
		if(spruecheInitialSize == 0)
			spruecheInitialSize = sprueche.size();
		
		if (UebersetzButton.getText().equals("Neu"))
			switchText("");
		try {
			// Alle Sprüche durchgehen, dann Easter egg:
			String Spruch = "";
			if (sprueche.size()==0) {
				String name = "dude";
				try {
					name = System.getProperty("user.name");
				} catch (Exception e) {
					e.printStackTrace();
					DialogBoxes.showErrorBox("Error", "Ein Fehler ist aufgetreten!", e.getMessage());
				}
				Spruch = "Du bist bereits alle " + spruecheInitialSize + " Sprüche durchgegangen. Get a life, " + name + "!";
			}
			else {
				int spruechezahl = new Random().nextInt(sprueche.size());
				Spruch = sprueche.get(spruechezahl);
				sprueche.remove(Spruch);
			}
			TextFeld.setText(Spruch);
			
		} catch (Exception e) {
			e.printStackTrace();
			DialogBoxes.showErrorBox("Error", "Ein Fehler ist aufgetreten!", e.getMessage());
		}
	}

	public String translateWords(String word) {

		boolean translated = false;
		// Anzahl an Fehlern: (wählbar mit bar)
		int limit = probValue;

		boolean wasBigLetter = false;
		boolean vonUsed = false;

		// too short, no change
		if (word.length() < 2
				|| word.contains("8")
				|| word.contains("1"))
			translated = true;
		else {

			// von -> vong, (her am ende vong satz((nach nächstem Wort wenn
			// großbuchst.)
			if (word.equals("von")) {
				word = word.replace("von", "vong");
				vonUsed = true;
				translated = true;
			}
			if (word.equals("Leben") || word.equals("leben")) {
				word = word.replace("Leben", "life");
				word = word.replace("leben", "live");
				translated = true;
			}
			if (word.equals("essen") || word.equals("trinken") || word.equals("kaufen")) {
				word = word.replace("essen", "gönnen");
				word = word.replace("trinken", "gönnen");
				word = word.replace("kaufen", "gönnen");
				translated = true;
			}
			if (word.equals("esse") || word.equals("trinke") || word.equals("kaufe")) {
				word = word.replace("esse", "gönne");
				word = word.replace("trinke", "gönne");
				word = word.replace("kaufe", "gönne");
				translated = true;
			}
			if (word.equals("iss") || word.equals("trink") || word.equals("kauf")) {
				word = word.replace("iss", "gönn");
				word = word.replace("trink", "gönn");
				word = word.replace("kauf", "gönn");
				translated = true;
			}
			if (word.equals("isst") || word.equals("trinkst") || word.equals("kaufst")) {
				word = word.replace("isst", "gönnst");
				word = word.replace("trinkst", "gönnst");
				word = word.replace("kaufst", "gönnst");
				translated = true;
			}
			if (word.equals("esst") || word.equals("trinkt") || word.equals("kauft")) {
				word = word.replace("esst", "gönnt");
				word = word.replace("trinkt", "gönnt");
				word = word.replace("kauft", "gönnt");
				translated = true;
			}
			if (word.equals("herz") || word.equals("Herz")) {
				word = word.replace("herz", "<3");// &#x2764;
				word = word.replace("Herz", "<3");// &#x2764;
				translated = true;
			}

			// sch & s austauschen
			if (word.contains("sch")) {
				String wordHelper = word.replace("sch", "1283460123weef$iewr3r346324fr43");
				if (wordHelper.contains("s")) {
					word = wordHelper.replace("s", "cwezoc%bewuc9&8575434554687df");

					word = word.replace("1283460123weef$iewr3r346324fr43", "s");
					word = word.replace("cwezoc%bewuc9&8575434554687df", "sch");
					translated = true;
				}
			}

			// big -> small letter
			if (Character.isUpperCase(word.charAt(0)) && translated == false
					&& (new Random().nextInt(100) + 1 < (limit / 2))) {
				word = word.toLowerCase();
				translated = true;
				wasBigLetter = true;
			}

			// Major changes
			if (word.contains("mich") && translated == false && (new Random().nextInt(100) + 1 < limit * 4)) {
				word = word.replace("mich", "mir");
				translated = true;
			}
			if (word.contains("ich") && translated == false && (new Random().nextInt(100) + 1 < limit * 4)
					&& word.length() < 4) {
				word = word.replace("ich", "i");
				translated = true;
			}
			if (word.contains("fürs") && translated == false && (new Random().nextInt(100) + 1 < limit * 4)) {
				word = word.replace("fürs", "für");
				translated = true;
			}
			if (word.contains("für das") && translated == false && (new Random().nextInt(100) + 1 < limit * 4)) {
				word = word.replace("für das", "für");
				translated = true;
			}
			if (word.contains("schon") && translated == false && (new Random().nextInt(100) + 1 < limit * 4)) {
				word = word.replace("schon", "schong");
				translated = true;
			}
			if (word.contains("schaun") && translated == false && (new Random().nextInt(100) + 1 < limit * 4)) {
				word = word.replace("schaun", "schaung");
				translated = true;
			}
			if (word.contains("spielen") && translated == false && (new Random().nextInt(100) + 1 < limit * 4)) {
				word = word.replace("spielen", "gspieln");
				translated = true;
			}
			if (word.contains("hinaus") && translated == false && (new Random().nextInt(100) + 1 < limit * 4)) {
				word = word.replace("hinaus", "raus");
				translated = true;
			}
			if (word.contains("iti") && translated == false && (new Random().nextInt(100) + 1 < limit * 4)) {
				word = word.replace("iti", "izi");
				translated = true;
			}
			if (word.contains("000") && translated == false && (new Random().nextInt(100) + 1 < limit * 4)) {
				word = word.replace("000", "00000000");
				translated = true;
			}
			if (word.contains("joe") && translated == false && (new Random().nextInt(100) + 1 < limit * 4)) {
				word = word.replace("joe", "jo");
				translated = true;
			}
			if (word.contains("like") && translated == false && (new Random().nextInt(100) + 1 < limit * 4)) {
				word = word.replace("like", "&hearts;");
				translated = true;
			}
			if (word.contains("das") && translated == false && (new Random().nextInt(100) + 1 < limit * 4)) {
				word = word.replace("das", "des");
				translated = true;
			}
			if (word.equals("ist") && translated == false && (new Random().nextInt(100) + 1 < limit)) {
				word = word.replace("ist", "sind");
				translated = true;
			}
			if (word.equals("sind") && translated == false && (new Random().nextInt(100) + 1 < limit)) {
				word = word.replace("sind", "ist");
				translated = true;
			}

			// Word starts with:
			if (word.startsWith("Süd") && translated == false && (new Random().nextInt(100) + 1 < limit)) {
				word = word.replace("Süd", "süd ");
				translated = true;
			}
			if (word.startsWith("Nord") && translated == false && (new Random().nextInt(100) + 1 < limit)) {
				word = word.replace("Nord", "nord ");
				translated = true;
			}
			if (word.startsWith("Ost") && translated == false && (new Random().nextInt(100) + 1 < limit)) {
				word = word.replace("Ost", "ost ");
				translated = true;
			}
			if (word.startsWith("West") && translated == false && (new Random().nextInt(100) + 1 < limit)) {
				word = word.replace("West", "west ");
				translated = true;
			}
			if (word.startsWith("ent") && translated == false && (new Random().nextInt(100) + 1 < limit)) {
				word = word.replace("ent", "emp");
				translated = true;
			}
			if (word.startsWith("emp") && translated == false && (new Random().nextInt(100) + 1 < limit)) {
				word = word.replace("emp", "ent");
				translated = true;
			}

			// Word ends with:
			if (word.endsWith("en") && translated == false && (new Random().nextInt(100) + 1 < limit)
					&& word.length() > 4) {
				int tempRand = new Random().nextInt(3) + 1;
				if (tempRand == 1)
					word = word.replace("en", "n");
				else if (tempRand == 2)
					word = word.replace("en", "eng");
				else
					word = word.replace("en", "");

				translated = true;
			}
			if (word.endsWith("ht") && translated == false && (new Random().nextInt(100) + 1 < limit)) {
				word = word.replace("ht", "hst");
				translated = true;
			}
			if (word.endsWith("re") && translated == false && (new Random().nextInt(100) + 1 < limit)) {
				word = word.replace("re", "r");
				translated = true;
			}
			if (word.endsWith("st") && translated == false && (new Random().nextInt(100) + 1 < limit)) {
				word = word.replace("st", "s");
				translated = true;
			}
			if (word.endsWith("ne") && translated == false && (new Random().nextInt(100) + 1 < limit)) {
				word = word.replace("ne", "n");
				translated = true;
			}

			if (word.endsWith("y") && word.length() > 5 && translated == false
					&& (new Random().nextInt(100) + 1 < limit)) {
				word = word.replace("y", "ie");
				translated = true;
			}
			if (word.endsWith("ie") && word.length() > 5 && translated == false
					&& (new Random().nextInt(100) + 1 < limit)) {
				word = word.replace("ie", "y");
				translated = true;
			}
			if (word.endsWith("ie") && translated == false && (new Random().nextInt(100) + 1 < limit)) {
				word = word.replace("ie", "y");
				translated = true;
			}
			if (word.endsWith("ts") && translated == false && (new Random().nextInt(100) + 1 < limit)) {
				word = word.replace("ts", "tz");
				translated = true;
			}
			if (word.endsWith("tz") && translated == false && (new Random().nextInt(100) + 1 < limit)) {
				word = word.replace("tz", "ts");
				translated = true;
			}
			if (word.endsWith("eit") && translated == false && (new Random().nextInt(100) + 1 < limit)) {
				word = word.replace("eit", "ein");
				translated = true;
			}
			if (word.endsWith("ein") && translated == false && (new Random().nextInt(100) + 1 < limit)) {
				word = word.replace("ein", "eit");
				translated = true;
			}

			// Nothing found so far, now:
			if (translated == false) {
				if (word.contains("ö") && translated == false && (new Random().nextInt(100) + 1 < limit)) {
					word = word.replace("ö", "öh");
					translated = true;
				}
				if (word.contains("ä") && translated == false && (new Random().nextInt(100) + 1 < limit)) {
					if ((new Random().nextInt(100) + 1) > 50)
						word = word.replace("ä", "äh");
					else
						word = word.replace("ä", "e");
					translated = true;
				}
				if (word.contains("ü") && translated == false && (new Random().nextInt(100) + 1 < limit)) {
					word = word.replace("ü", "üh");
					translated = true;
				}
				if (word.contains("n") && translated == false && (new Random().nextInt(100) + 1 < limit)) {
					word = word.replace("n", "m");
					translated = true;
				}
				if (word.contains("d") && translated == false && (new Random().nextInt(100) + 1 < limit)) {
					word = word.replace("d", "t");
					translated = true;
				}
				if (word.contains("b") && translated == false && (new Random().nextInt(100) + 1 < limit)) {
					word = word.replace("b", "p");
					translated = true;
				}
				if (word.contains("tzt") && translated == false && (new Random().nextInt(100) + 1 < limit)) {
					word = word.replace("tzt", "zt");
					translated = true;
				}
				if (word.contains("ter") && translated == false && (new Random().nextInt(100) + 1 < limit)) {
					word = word.replace("ter", "tr");
					translated = true;
				}
				if (word.contains("fa") && translated == false && (new Random().nextInt(100) + 1 < limit)) {
					word = word.replace("fa", "fla");
					translated = true;
				}
				if (word.contains("ver") && translated == false && (new Random().nextInt(100) + 1 < limit)) {
					word = word.replace("ver", "ve");
					translated = true;
				}
				if (word.contains("k") && translated == false && (new Random().nextInt(100) + 1 < limit)) {
					word = word.replace("k", "g");
					translated = true;
				}
				if (word.contains("m") && translated == false && (new Random().nextInt(100) + 1 < limit)) {
					word = word.replace("m", "n");
					translated = true;
				}
				if (word.contains("t") && translated == false && (new Random().nextInt(100) + 1 < limit)) {
					word = word.replace("t", "d");
					translated = true;
				}
				if (word.contains("p") && translated == false && (new Random().nextInt(100) + 1 < limit)) {
					word = word.replace("p", "b");
					translated = true;
				}
				if (word.contains("g") && translated == false && (new Random().nextInt(100) + 1 < limit)) {
					word = word.replace("g", "k");
					translated = true;
				}
				if (word.contains("ss") && translated == false && (new Random().nextInt(100) + 1 < limit)) {
					word = word.replace("ss", "s");
					translated = true;
				}
				if (word.contains("ist") && translated == false && (new Random().nextInt(100) + 1 < (limit / 3))) {
					word = word.replace("ist", "is");
					translated = true;
				}
				if (word.contains("ll") && translated == false && (new Random().nextInt(100) + 1 < limit)) {
					word = word.replace("ll", "l");
					translated = true;
				}
				if (word.contains("pp") && translated == false && (new Random().nextInt(100) + 1 < limit)) {
					word = word.replace("pp", "p");
					translated = true;
				}
				if (word.contains("nn") && translated == false && (new Random().nextInt(100) + 1 < limit)) {
					word = word.replace("nn", "n");
					translated = true;
				}
				if (word.contains("tt") && translated == false && (new Random().nextInt(100) + 1 < limit)) {
					word = word.replace("tt", "dt");
					translated = true;
				}
				if (word.contains("ch") && translated == false && (new Random().nextInt(100) + 1 < limit)) {
					word = word.replace("ch", "g");
					translated = true;
				}
				if (word.contains("ph") && translated == false && (new Random().nextInt(100) + 1 < limit)) {
					word = word.replace("ph", "f");
					translated = true;
				}
				if (word.contains("i") && translated == false && (new Random().nextInt(100) + 1 < limit)) {
					word = word.replace("i", "ie");
					translated = true;
				}
				if (word.contains("ft") && translated == false && (new Random().nextInt(100) + 1 < limit)) {
					word = word.replace("ft", "f");
					translated = true;
				}
				if (word.contains("y") && translated == false && (new Random().nextInt(100) + 1 < limit)) {
					word = word.replace("y", "ü");
					translated = true;
				}
				if (word.contains(" am ") && translated == false && (new Random().nextInt(100) + 1 < limit)) {
					word = word.replace(" am ", " in ");
					translated = true;
				}
				if (word.contains("ß") && translated == false && (new Random().nextInt(100) + 1 < limit)) {
					word = word.replace("ß", "s");
					translated = true;
				}
				if (word.contains("es") && translated == false && (new Random().nextInt(100) + 1 < limit)) {
					word = word.replace("es", "e");
					translated = true;
				}
				if (word.contains("v") && translated == false && (new Random().nextInt(100) + 1 < limit)) {
					word = word.replace("v", "w");
					translated = true;
				}
				if (word.contains("mm") && translated == false && (new Random().nextInt(100) + 1 < limit)) {
					word = word.replace("mm", "nn");
					translated = true;
				}
				if (word.contains("ee") && translated == false && (new Random().nextInt(100) + 1 < limit)) {
					word = word.replace("ee", "eh");
					translated = true;
				}
				if (word.contains("et") && translated == false && (new Random().nextInt(100) + 1 < limit)) {
					word = word.replace("et", "ent");
					translated = true;
				}
				if (word.contains("i") && translated == false && (new Random().nextInt(100) + 1 < limit)) {
					word = word.replace("i", "e");
					translated = true;
				}
				// letzter Buchstabe weg (unwahrscheinlicher & nicht bei zu kurzen Wörtern)
				if ((new Random().nextInt(100) + 1 < (limit / 2)) && translated == false && word.length() > 5) {
					word = word.substring(0, word.length() - 1);
					translated = true;
				}
			}

			if (word.contains(".") && (new Random().nextInt(25) + 1 < ((limit + 1) / 2)) || word.contains("...")) {
				String[] emojis = { " lol.", " :) ", " ;) ", " B) ", " :-) ",
						" :D ", " lmao." };
				int tempRand = new Random().nextInt(emojis.length);
				word = word.replace(".", emojis[tempRand]);
				translated = true;
			}

			if (word.contains(".") && vonUsed) {
				vonUsed = false;
				word = word.replace(".", " her.");
			}
			if (Character.isUpperCase(word.charAt(0)) && vonUsed || wasBigLetter && vonUsed) {
				vonUsed = false;
				word = word + " her";
			}
			if ((new Random().nextInt(100) + 1) < limit && translated == false) {
				word = check4twice(word);
			}
		}
		return word;
	}

	public String check4twice(String word) {

		if (word.contains("aa")) {
			word = word.replace("aa", "a");
		}
		if (word.contains("bb")) {
			word = word.replace("bb", "b");
		}
		if (word.contains("cc")) {
			word = word.replace("cc", "c");
		}
		if (word.contains("dd")) {
			word = word.replace("dd", "d");
		}
		if (word.contains("ee")) {
			word = word.replace("ee", "e");
		}
		if (word.contains("ff")) {
			word = word.replace("ff", "f");
		}
		if (word.contains("gg")) {
			word = word.replace("gg", "g");
		}
		if (word.contains("hh")) {
			word = word.replace("hh", "h");
		}
		if (word.contains("ii")) {
			word = word.replace("ii", "i");
		}
		if (word.contains("jj")) {
			word = word.replace("jj", "j");
		}
		if (word.contains("kk")) {
			word = word.replace("kk", "k");
		}
		if (word.contains("ll")) {
			word = word.replace("ll", "l");
		}
		if (word.contains("mm")) {
			word = word.replace("mm", "m");
		}
		if (word.contains("nn")) {
			word = word.replace("nn", "n");
		}
		if (word.contains("oo")) {
			word = word.replace("oo", "o");
		}
		if (word.contains("pp")) {
			word = word.replace("pp", "p");
		}
		if (word.contains("qq")) {
			word = word.replace("qq", "q");
		}
		if (word.contains("rr")) {
			word = word.replace("rr", "r");
		}
		if (word.contains("ss")) {
			word = word.replace("ss", "s");
		}
		if (word.contains("tt")) {
			word = word.replace("tt", "t");
		}
		if (word.contains("uu")) {
			word = word.replace("uu", "u");
		}
		if (word.contains("vv")) {
			word = word.replace("vv", "v");
		}
		if (word.contains("ww")) {
			word = word.replace("ww", "w");
		}
		if (word.contains("xx")) {
			word = word.replace("xx", "x");
		}
		if (word.contains("yy")) {
			word = word.replace("yy", "y");
		}
		if (word.contains("zz")) {
			word = word.replace("zz", "z");
		}

		return word;
	}

	@FXML
	public void setProbabValueTo10() {
		probSplitMenu.setText("10");
		probValue = 10;
	}

	@FXML
	public void setProbabValueTo20() {
		probSplitMenu.setText("20");
		probValue = 20;
	}

	@FXML
	public void setProbabValueTo30() {
		probSplitMenu.setText("30");
		probValue = 30;
	}

	@FXML
	public void setProbabValueTo40() {
		probSplitMenu.setText("40");
		probValue = 40;
	}

	@FXML
	public void setProbabValueTo50() {
		probSplitMenu.setText("50");
		probValue = 50;
	}

	@FXML
	public void setProbabValueTo60() {
		probSplitMenu.setText("60");
		probValue = 60;
	}

	@FXML
	public void setProbabValueTo70() {
		probSplitMenu.setText("70");
		probValue = 70;
	}

	@FXML
	public void setProbabValueTo80() {
		probSplitMenu.setText("80");
		probValue = 80;
	}

	@FXML
	public void setProbabValueTo90() {
		probSplitMenu.setText("90");
		probValue = 90;
	}

	@FXML
	public void setProbabValueTo100() {
		probSplitMenu.setText("100");
		probValue = 100;
	}

	@FXML
	public void clear() {
		if (UebersetzButton.getText().equals("Neu"))
			switchText("");
		TextFeld.setText("");
	}

	@FXML
	public void showAboutInfo() {
		DialogBoxes.showMessageBox("Info", "Nachdenkliche Texte\n\nVersion 1.0.0",
				"© GerH 2017, Dev Github: gh28942");
	}

	@FXML
	public void loadText() {
		try {
			FileChooser chooser = new FileChooser();
			chooser.setTitle("Öffne Text Datei");
			chooser.getExtensionFilters().addAll(new ExtensionFilter("Text Dateien", "*.pdf", "*.txt"));
			File selectedFile = chooser.showOpenDialog(new Stage());

			if (selectedFile != null) {
				String fileName = selectedFile.getName();
				String fileText = "";
				if (fileName.endsWith(".txt")) {
					TextFeld.setText("Lade Text, bitte warte...");
					BufferedReader in = new BufferedReader(new FileReader(selectedFile));
					String line;
					while ((line = in.readLine()) != null) {
						fileText = fileText + line + "\n";
					}
					in.close();
					TextFeld.setText(fileText);
				} else if (fileName.endsWith(".pdf")) {
					TextFeld.setText("Lade Text, bitte warte...");
					PDDocument document = null;
					document = PDDocument.load(selectedFile);
					document.getClass();
					if (!document.isEncrypted()) {
						PDFTextStripperByArea stripper = new PDFTextStripperByArea();
						stripper.setSortByPosition(true);
						PDFTextStripper Tstripper = new PDFTextStripper();
						fileText = Tstripper.getText(document);
					} else {
						DialogBoxes.showErrorBox("Error", "Die Datei ist encrypted!.", "");
					}
					document.close();
					TextFeld.setText(fileText);
				} else {
					DialogBoxes.showErrorBox("Error", "Datei konnte nicht geladen werden.", "");
				}
				DialogBoxes.showMessageBox("", "File selected: " + selectedFile.getName(), "");
			}
		} catch (Exception e) {
			e.printStackTrace();
			DialogBoxes.showErrorBox("Error", "Ein Fehler ist aufgetreten!", e.getMessage());
		}
	}

	@FXML
	public void entferneParagraph() {
		if (UebersetzButton.getText().equals("Neu"))
			switchText("");
		String TextFeldText = TextFeld.getText();
		TextFeldText = TextFeldText.replace("\n", " ");
		TextFeld.setText(TextFeldText);
	}

	public void addrandomBackgroundImg() {
		
		//Get random pic from the BG pics. Since these five lines don't work in an executable JAR (but in the IDE), the less clean solution below is necessary.
		//final File dir = new File("image/images/");
		//File[] files = dir.listFiles();
		//Random rand = new Random();
		//File file = files[rand.nextInt(files.length)];
		//Image image = new Image(file.toURI().toString());
		
		String[] bg_pictures = {"/images/alcedo-atthis-881594_1920.jpg", "/images/andromeda-galaxy-755442_1280.jpg", "/images/animal-21649_1920.jpg", "/images/animals-102483_1920.jpg", "/images/art-1478831_1920.jpg", "/images/autumn-2815427_1920.jpg", "/images/background-1587514_1920.jpg", "/images/background-2062206_1920.jpg", "/images/background-2277_1920.jpg", "/images/background-2956789_1920.jpg", "/images/balloon-3206530_1920.jpg", "/images/bench-1245994_1920.jpg", "/images/blue-1553203_1920.jpg", "/images/brush-96240_1920.jpg", "/images/butterfly-3054736_1920.jpg", "/images/coast-3358820_1920.jpg", "/images/dark-788997_1920.jpg", "/images/daylily-3512540_1920.jpg", "/images/desert-1840453_1920.jpg", "/images/drip-4267390_1920.jpg", "/images/earth-3355931_1920.jpg", "/images/evening-1038148_1920.jpg", "/images/fern-2332262_1920.jpg", "/images/flower-1514032_1920.jpg", "/images/flower-2372985_1920.jpg", "/images/flower-2372987_1920.jpg", "/images/flower-2372997_1920.jpg", "/images/flower-2372998_1920.jpg", "/images/flower-3175428_1920.jpg", "/images/fog-3622519_1920.jpg", "/images/garlic-84691_1920.jpg", "/images/gifts-2998593_1920.jpg", "/images/girl-1990347_1920.jpg", "/images/graffiti-1450798_1920.jpg", "/images/handwriting-1362879_1920.jpg", "/images/heart-1192662_1920.jpg", "/images/landscape-4765322_1920.jpg", "/images/landscape-914148_1920.jpg", "/images/leaf-1001679_1920.jpg", "/images/leaf-318743_1920.jpg", "/images/leaves-336694_1920.jpg", "/images/leaves-4673997_1920.jpg", "/images/light-1282314_1920.jpg", "/images/light-bulb-4514505_1920.jpg", "/images/love-1731755_1920.jpg", "/images/lusen-3447463_1920.jpg", "/images/luxury-home-2975792_1920.jpg", "/images/macaroons-2178371_1920.jpg", "/images/map-of-the-world-2401458_1920.jpg", "/images/milky-way-1023340_1920.jpg", "/images/milky-way-2695569_1920.jpg", "/images/milky-way-923738_1920.jpg", "/images/moon-1527501_1920.jpg", "/images/mosaic-1344858_1920.jpg", "/images/mountains-3523153_1920.jpg", "/images/nature-3046567_1920.jpg", "/images/night-1846734_1920.jpg", "/images/northern-lights-3273425_1920.jpg", "/images/ocean-3605547_1920.jpg", "/images/office-3237095_1920.jpg", "/images/painting-316398_1280.jpg", "/images/palm-trees-3058728_1920.jpg", "/images/peacock-453846_1280.jpg", "/images/pencil-1209528_1920.jpg", "/images/pencils-452238_1920.jpg", "/images/pillows-820149_1280.jpg", "/images/planet-2120004_1920.jpg", "/images/pt-robinson-lighthouse-3457166_1920.jpg", "/images/pumpkin-4454745_1920.jpg", "/images/puzzle-3223941_1920.jpg", "/images/rain-455124_1920.jpg", "/images/rocks-1246668_1920.jpg", "/images/rose-3142660_1920.jpg", "/images/rose-petals-3194062_1920.jpg", "/images/sea-3652697_1920.jpg", "/images/shell-1031290_1920.jpg", "/images/ship-traffic-jams-602169_1280.jpg", "/images/smoke-376543_1920.jpg", "/images/splashing-165192_1280.jpg", "/images/stone-3630911_1920.jpg", "/images/stone-wall-3558499_1920.jpg", "/images/succulents-3693409_1920.jpg", "/images/sunflower-3792914_1920.jpg", "/images/sunrise-1756274_1920.jpg", "/images/sunset-3689760_1920.jpg", "/images/tea-plantation-3358870_1920.jpg", "/images/towel-1838210_1920.jpg", "/images/tree-4450514_1920.jpg", "/images/tulips-21620_1920.jpg", "/images/tulips-21690_1920.jpg", "/images/tulips-2544_1920.jpg", "/images/tulips-3251607_1920.jpg", "/images/valentine-1953964_1920.jpg", "/images/vintage-1170654_1920.jpg", "/images/water-839590_1920.jpg", "/images/water-918461_1920.jpg", "/images/waterfall-2115206_1920.jpg", "/images/wave-384385_1920.jpg", "/images/wood-3212803_1920.jpg", "/images/wood-591631_1920.jpg"};
		Random r = new Random();
        int rand = r.nextInt(bg_pictures.length);
		Image image = new Image(bg_pictures[rand]);
		
		//Fit by width
		BackgroundImgView.fitHeightProperty().bind(image.heightProperty());
		BackgroundImgView.setImage(image);
	}

	@FXML
	public void zufaelligDrehen() {
		textLabel.setRotate((new Random().nextInt(30 - (-30) + 1) + (-30)));
	}
	
	@FXML
	public void geradeDrehen() {
		textLabel.setRotate(0);
	}

	@FXML
	public void bildAnAb() {
		switchText("");
	}
	
	public void switchText(String text) {
		try {
			if (UebersetzButton.getText().equals("\u00dcbersetzen")) {
				UebersetzButton.setText("Neu");
				textLabel.setText(text);

				String[] colorArray = { "#ffa500", "#ffd106", "#ffffc0", "#ffffa8", "#ffff00", "#ffffc4", "#ffffdd",
						"#fddc68", "#fee911", "#f9ed37", "#fcfa85", "#fcf852", "#d7fdec", "#d7edec", "#d7ddec",
						"#d7cdec", "#d7bdec", "#00ffec", "#ff0000", "#89ff00", "#fff400", "#e300ff", "#fc59a3",
						"#87c830", "#ffd400", "#fe7e0f", "#8e3ccb", "#00f9ff", "#5ff4da", "#ffe441", "#f2f411",
						"#b8ff75", "#ffc100", "#c356ea", "#8ff243", "#71aef2", "#ea5645", "#ff0000", "#1dff00",
						"#ff8a00", "#fcff00", "#ffca31", "#00fff9", "#ffdb00", "#15ff00", "#ff3300", "#8900ff",
						"#1322a6", "#e81111", "#5a36a3", "#106125", "#abd91f", "#f4f1af", "#f7f960", "#fff400",
						"#e3ff00", "#a7a10a", "#ff5b5b", "#ffc75d", "#b8ff52", "#91f7ff", "#ff82d4", "#91aaff",
						"#ff9e9e", "#ff80c5", "#7afbff", "#8aff9c", "#fff518", "#ffc100", "#55b6ff", "#76ff0c",
						"#bd6fff", "#ff7272", "#ffca78", "#fdff58", "#99ff2d", "#31ffdc", "#9600ff", "#bd00ff",
						"#ff00e7", "#ff008d", "#ff005a", "#e1e6f5", "#efefef", "#f3ffea", "#ffe9e9", "#ffd8f8",
						"#ff0000", "#1dff00", "#ff8a00", "#fcff00", "#ffca31", "#ba1818", "#336927", "#a3ff00",
						"#008080", "#9500d8", "#daf0aa", "#b5e37f", "#9fc360", "#ffec7b", "#ecd860",
						"#ffffff" };

				textLabel.setTextFill(Color.web(colorArray[new Random().nextInt(colorArray.length)]));

				textLabel.setVisible(true);
				TextFeld.setVisible(false);
				addrandomBackgroundImg();
				BackgroundImgView.setVisible(true);

			} else { 
				UebersetzButton.setText("\u00dcbersetzen");
				textLabel.setVisible(false);
				TextFeld.setVisible(true);
				BackgroundImgView.setVisible(false);
			}
		} catch (Exception e) {
			e.printStackTrace();
			DialogBoxes.showErrorBox("Error", "Ein Fehler ist aufgetreten!", e.getMessage());
		}

	}

	@FXML
	public void addrandomPic() {
		addrandomBackgroundImg();
	}

	@FXML
	public void addrandomColor() {
		String[] colorArray = { "#4b1657", "#eed664", "#ead966", "#82ae67", "#75a557", "#ffea73", "#c24643", "#eeda6a",
				"#eeda6a", "#8be9fd", "#ff99ff", "#ff80ff", "#daddea", "#d6b4ed", "#7c9ee1", "#009385", "#8ad000",
				"#00cc99", "#009b7e", "#fdd305", "#d8e6f0", "#ffff00", "#fcfcf7", "#141493", "#89c6de", "#ff8690",
				"#999cd8", "#f59e25", "#a0053a", "#a4a4d0", "#8ad6d6", "#d68a8a", "#ffb6c1", "#99dbe3", "#f6e6d9",
				"#e3f0db", "#c3b186", "#4d3619", "#7a6856", "#82ae67", "#75a557", "#34e587", "#0076a3" };
		textLabel.setTextFill(Color.web(colorArray[new Random().nextInt(colorArray.length)]));
	}

	@FXML
	public void loadPic() {
		try {
			FileChooser chooser = new FileChooser();
			chooser.setTitle("Lade Bild");
			chooser.getExtensionFilters().addAll(new ExtensionFilter("Bild Dateien", "*.jpg", "*.png", "*.gif"));
			File selectedFile = chooser.showOpenDialog(new Stage());
			String selectedFileStr = selectedFile.toString();

			BufferedImage image = ImageIO.read(new File(selectedFileStr));
			Image realImg = SwingFXUtils.toFXImage(image, null);
			BackgroundImgView.setImage(realImg);
		} catch (Exception e) {
			e.printStackTrace();
			DialogBoxes.showErrorBox("Error", "Ein Fehler ist aufgetreten!", e.getMessage());
		}
	}

	@FXML
	public void saveAsPng() {
		//take photo
		SnapshotParameters mSnapshotParameters = new SnapshotParameters();
		mSnapshotParameters.setFill(Color.BLACK);
		Rectangle2D toPaint = new Rectangle2D(BackgroundImgView.getX()+6, BackgroundImgView.getY()+31,
				BackgroundImgView.getFitWidth(), BackgroundImgView.getY()+479);
		mSnapshotParameters.setViewport(toPaint);
		WritableImage image = picPane.snapshot(mSnapshotParameters, null);

		//create file with current datetime
		String sdf = LocalDateTime.now().format(DateTimeFormatter.ofPattern("_yyyy-MM-dd_HH-mm-ss-SSS"));
		File file = new File("screenshot" + sdf + ".png");

		try {
			ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
			DialogBoxes.showMessageBox("Bild gespeichert", "Der Screenshot wurde erfolgreich gespeichert!",
					"Du kannst das Bild unter \n\"" + file.getAbsolutePath() + "\"\n finden.");
		} catch (IOException e) {
			e.printStackTrace();
			DialogBoxes.showErrorBox("Error", "Ein Fehler ist aufgetreten!", e.getMessage());
		}
	}
}
